; 10 hw

(defn constant [x]
      (constantly x))

(defn variable [name]
      (fn [m]
          (first
            (mapv m [name]))))

(defn fun [f]
      (fn [& args]
          (fn [m]
              (def vargs (mapv #(% m) args))
              (apply f vargs))))

(def add (fun +))

(def subtract (fun -))

(def multiply (fun *))

(defn div [x y]
      (/ (double x) (double y)))

(def divide (fun div))

(def negate subtract)

(def pow (fun (fn [x y] (Math/pow x y))))

(def log (fun (fn [x y]
                  (div (Math/log (Math/abs y))
                       (Math/log (Math/abs x))))))

(def operators {
                '+ add,
                '- subtract,
                '* multiply,
                '/ divide,
                'negate negate,
                'pow pow,
                'log log
                })

(defn parseFunction [expr]
      (cond
        (number? expr) (constant expr),
        (symbol? expr) (variable (str expr)),
        (string? expr) (parseFunction (read-string expr)),
        (seq? expr) (apply (get operators (first expr)) (mapv parseFunction (rest expr)))))

; 11 hw

(defn proto-get
      ([obj key] (proto-get obj key nil))
      ([obj key default]
       (cond
         (contains? obj key) (get obj key)
         (contains? obj :proto) (recur (obj :proto) key default)
         :else default)))

(defn proto-call [obj key & args]
      (apply (proto-get obj key) obj args))

(defn field
      ([key] (field key nil))
      ([key default] (fn [obj] (proto-get obj key))))

(defn method [key]
      (fn [obj & args] (apply proto-call obj key args)))

(def _operands (field :operands))
(def _lexeme (field :lexeme))
(def _function (field :function))

(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))

(def Constant)
(def ConstantPrototype {
                        :evaluate (fn [this args] (_operands this))
                        :diff (fn [this args] (Constant 0))
                        :toString (fn [this] (format "%.1f" (double (_operands this))))
                        })

(defn Constant [num] {
                      :proto ConstantPrototype
                      :operands num
                      })

(def VariablePrototype {
                        :evaluate (fn [this args] (args (_operands this)))
                        :diff (fn [this args] (if (= (_operands this) args)
                                                (Constant 1)
                                                (Constant 0)))
                        :toString (fn [this] (_operands this))
                        })

(defn Variable [args] {
                       :proto VariablePrototype
                       :operands args
                       })

(defn Operation [lexeme function diff]
      (fn [& operands]
          {
           :lexeme lexeme
           :function function
           :diff diff
           :operands (vec operands)

           :evaluate (fn [this args]
                         (apply (_function this)
                                (mapv (fn [arg] (evaluate arg args))
                                      (_operands this))))
           :toString (fn [this] (str "(" (_lexeme this) " "
                                     (clojure.string/join " " (mapv toString (_operands this))) ")"))
           }))

(def nth-operand
  (fn [this id] ((_operands this) id)))
(def nth-diff
  (fn [this id var] (diff ((_operands this) id) var)))

(def diff-operands
  (fn [this var] (map
                   (fn [arg] (diff arg var)) (_operands this))))

(def Add
  (Operation '+ +
             (fn [this var]
                 (apply Add (diff-operands this var)))))

(def Subtract
  (Operation '- -
             (fn [this var]
                 (apply Subtract (diff-operands this var)))))

(def Multiply
  (Operation '* *
             (fn [this var] (Add
                              (Multiply (nth-diff this 0 var) (nth-operand this 1))
                              (Multiply (nth-diff this 1 var) (nth-operand this 0))))))

(def Divide
  (Operation '/ (fn [x y] (/ x (double y)))
             (fn [this var] (Divide (Subtract
                                      (Multiply (nth-diff this 0 var) (nth-operand this 1))
                                      (Multiply (nth-diff this 1 var) (nth-operand this 0)))
                                    (Multiply (nth-operand this 1) (nth-operand this 1)))
                 )))

(def Negate
  (Operation 'negate -
             (fn [this var]
                 (apply Negate (diff-operands this var)))))

(def E (Constant Math/E))

(def Log
  (Operation 'log (fn [x y] (/ (Math/log (Math/abs y)) (Math/log (Math/abs x))))
             (fn [this var] (Divide
                              (Subtract
                                (Multiply
                                  (Log E (nth-operand this 0))
                                  (Divide (nth-diff this 1 var) (nth-operand this 1)))
                                (Multiply
                                  (Log E (nth-operand this 1))
                                  (Divide (nth-diff this 0 var) (nth-operand this 0))))
                              (Multiply (Log E (nth-operand this 0))
                                        (Log E (nth-operand this 0))))
                 )))

(def Pow
  (Operation 'pow (fn [x y] (Math/pow x y))
             (fn [this var] (Multiply
                              (Pow (nth-operand this 0) (nth-operand this 1))
                              (Add
                                (Multiply
                                  (Log E (nth-operand this 0))
                                  (nth-diff this 1 var))
                                (Multiply
                                  (nth-operand this 1)
                                  (diff (Log E (nth-operand this 0)) var))))
                 )))

(def objectOperators {
                      'constant Constant
                      '+ Add
                      '- Subtract
                      '* Multiply
                      '/ Divide
                      'negate Negate
                      'pow Pow
                      'log Log
                      })

(defn parseObj [expr]
      (cond
        (seq? expr) (apply (objectOperators (first expr)) (mapv parseObj (rest expr)))
        (symbol? expr) (Variable (str expr))
        (number? expr) (Constant expr)))

(def parseObject
  (comp parseObj read-string))