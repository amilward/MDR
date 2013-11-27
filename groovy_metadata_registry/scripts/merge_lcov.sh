#!/bin/bash

#DIR=$1

while read FILENAME; do
    LCOV_INPUT_FILES="$LCOV_INPUT_FILES -a \"$FILENAME\""
done < <( find reports/js/coverage/ -name lcov.info )

eval lcov "${LCOV_INPUT_FILES}" -o reports/js/coverage-compiled.lcov

sed -i.old 's#SF:./web-app/js/#SF:#g' reports/js/coverage-compiled.lcov
rm reports/js/coverage-compiled.lcov.old