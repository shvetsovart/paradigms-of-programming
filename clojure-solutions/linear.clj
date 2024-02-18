(defn shapeless [fun & args]
      (if (number? (first args))
        (apply fun args)
        (apply mapv (partial shapeless fun) args)))

(defn shapeless-fun [fun] (fn [& args] (apply shapeless fun args)))

(def s+ (shapeless-fun +))
(def s- (shapeless-fun -))
(def s* (shapeless-fun *))
(def sd (shapeless-fun /))

(def v+ (shapeless-fun +))
(def v- (shapeless-fun -))
(def v* (shapeless-fun *))
(def vd (shapeless-fun /))
(defn v*s [vector scal] (mapv (fn [x] (* x scal)) vector))

(defn scalar [& args] (apply + (apply v* args)))
(defn vect [[x1 x2 x3] [y1 y2 y3]] [(- (* x2 y3) (* x3 y2)),
                                    (- (* x3 y1) (* x1 y3)),
                                    (- (* x1 y2) (* x2 y1))])

(def m+ (shapeless-fun v+))
(def m- (shapeless-fun v-))
(def m* (shapeless-fun v*))
(def md (shapeless-fun vd))

(defn transpose [matrix] (apply mapv vector matrix))
(defn m*s [matrix scal] (mapv (fn [x] (v*s x scal)) matrix))
(defn m*v [matrix vector] (mapv (fn [x] (scalar x vector)) matrix))
(defn m*m [m1 m2] (mapv (fn [x] (mapv (fn [y] (scalar x y)) (transpose m2))) m1))