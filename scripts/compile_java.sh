
# Compile the java files
javac -cp ../lib/main.jar ../qfm_fi_modified/src/qfm_fi_mod/*.java

# Move the compiled files to the bin directory
mv ../qfm_fi_modified/src/qfm_fi_mod/*.class ../qfm_fi_modified/bin/qfm_fi_mod/

# Create the jar file
jar cvf ../qfm_fi_modified/bin/qfm_mod.jar -C ../qfm_fi_modified/bin/qfm_fi_mod/ .

# Run the jar file
# java -cp lib/main.jar:qfm_fi_modified/bin/qfm_mod.jar qfm_fi_mod/Main