chmod +x gradlew
./gradlew build
cd ./build/distributions
rm -rf prometheus-java-example
mkdir prometheus-java-example
tar -xvf -C prometheus-java-example prometheus-java-example-1.0.tar --strip-components 1
