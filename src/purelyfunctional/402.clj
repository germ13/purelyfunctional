(ns purelyfunctional.402)

(defn most-frequent [elements]
  (if (= [] elements) nil
    (loop [el elements
           rsum (apply merge (map #(hash-map (keyword (str %)) 0) (set elements)))]
      (if (empty? el)
        (symbol (key (apply max-key val rsum)))

        (recur (rest el)
               (update-in rsum
                          [(keyword (str (first el)))]
                          inc))))))

