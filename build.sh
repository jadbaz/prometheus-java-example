chmod +x gradlew
./gradlew build
wd=`pwd`
cd ./build/distributions
rm -rf prometheus-java-example
mkdir prometheus-java-example
tar -xvf prometheus-java-example-1.0.tar -C prometheus-java-example --strip-components 1
cd $wd
docker build -t prometheus-java-example .
