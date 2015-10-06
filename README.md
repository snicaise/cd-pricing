# pricing

Service REST permettant de connaitre le prix d'un vol.

Composant pricing de la démo GoCD-Ansible. Pour plus d'information voir [cd-infrastructure](https://github.com/snicaise/cd-infrastructure)

# Usage

Prérequis : maven 3 et java 8

Build et tests
```sh
mvn clean package
```

Execution
```sh
cd pricing-core
java -jar target/pricing-core.jar server server.yml

curl -v http://localhost:8060/pricing/price?origin=paris&destination=londres
```

# Tests

Executer les tests unitaire :
```sh
mvn test
```

Executer les tests composant :
```sh
mvn test -Pcomponent-tests
```

Executer les tests d'intégration :
```sh
mvn test -Pintegration-tests
```

Executer tous les tests :
```sh
mvn test -Pall-tests
```
