(ns writers-toolbox.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [writers-toolbox.core-test]))

(doo-tests 'writers-toolbox.core-test)

