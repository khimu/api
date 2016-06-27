mvn clean install -DskipTests=true
scp target/mj-scrapper-executable.jar root@107.170.234.144:/opt/mjscrapper/
