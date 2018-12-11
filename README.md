# Coolie The Code Generator

A code generator for java project.<br/>

the generator base on freemarker to generate code by template file.

## Quick Start

change directory to coolie/generator-shell.
```
cd <your_path_to_coolie>/coolie/generator-shell
```

use maven's spring-boot plugin to run the module.
```
mvn clean package spring-boot:run
```

see the description of commands.
```
coolie:>help
```

add the example template.
```
coolie:>template add <your_path_to_coolie>/coolie/example/ftl example
```

see if template had added.
```
coolie:>template list -d
```

use mysql to generate code，use example/example.sql to create schema and table
```
mysql>source <your_path_to_coolie>/example/example.sql
```

config the example/project.json to connect your mysql-server
```
{
  "connections": [
    {
      "name": "mysql",
      "protocol": "jdbc:mysql:",
      "host": "<host_of_your_mysql_server>",
      "port": "<port_of_your_mysql_server>",
      "schema": "coolie",
      "query": "autoReconnect=true",
      "user": "<your_mysql_user>",
      "auth": "<your_mysql_pwd>"
    }
  ]
}
```

now you can generate code by example/project.json configure.
```
coolie:>gen example <your_path_to_coolie>/coolie/example/project.json
```

## Example

there are some example of the usage of generator in the example directory.<br/>
more detailed usage instruction is coming soon.
