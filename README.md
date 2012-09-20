# clojure-demo

This project shows how to run your Clojure apps with [Cloud Foundry](http://cloudfoundry.com).

Here you can find a sample [Noir](http://webnoir.org) web app that will display the credentials for the Cloud Foundry services that you bind to it.

This project uses [claude](http://github.com/videlalvaro/claude), a library that exposes Cloud Foundry services to your Clojure apps.

## Demo App:

[![See a demo here](https://raw.github.com/videlalvaro/cloudfoundry-clojure-demo/master/doc/cljcf.png)](http://clojure-demo.cloudfoundry.com)]

## Deploying your Clojure apps to Cloud Foundry

We asume that you are using [leiningen](https://github.com/technomancy/leiningen) for your Clojure projects.

First you will add the latest version of [claude](http://github.com/videlalvaro/claude) into your project dependencies:

```clojure
[claude "0.2.1-SNAPSHOT"]
```

Then run `lein deps` to obtain the project dependencies.

Once you have your project set up then you  need to declare your project `main` function:

```clojure
:main clojure-demo.server
```

See this project `project.clj` file for more details.

Then on your main namespace you need to add a `:gen-class` like in this example:

```clojure
(ns clojure-demo.server
  (:gen-class)
  (:require [noir.server :as server]
            [claude.core :as cf]))
```

If you are creating a web app then another tweak you need to perform is to inform your app of the `port` in which it has to run. You can obtain the port from the `VMC_APP_PORT` environment variable. Here's an example:

```clojure
(defn -main [& m]
  (let [mode (if (cf/cloudfoundry?) :prod :dev)
        port (Integer. (get (System/getenv) "VMC_APP_PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'clojure-demo})))
```

Also as you can see in that code snippet we are using the `claude.core/cloudfoundry?` helper to see if we are running in the cloud. In that case we can set the web app environment to `:prod`.

So far we have a basic clojure project that can be run from a .jar file. Let's create that jar file and deploy it to Cloud Foundry. We asume that you are already logged into [Cloud Foundry](http://cloudfoundry.com).

```bash
mkdir cf
lein uberjar
cp target/clojure-demo-0.1.0-SNAPSHOT-standalone.jar cf/
cd cf
```
Now we are ready to deploy our app. In this case I will bind one instance of each service provided by Cloud Foundry therefore you will see a lot of output in the following transcript.

```bash
vmc push
Would you like to deploy from the current directory? [Yn]: Y
Application Name: clojure-demo
Detected a Standalone Application, is this correct? [Yn]: Y
1: java
2: java7
3: node
4: node06
5: node08
6: ruby18
7: ruby19
Select Runtime [java]: java
Selected java
Start Command: java $JAVA_OPTS -cp clojure-demo-0.1.0-SNAPSHOT-standalone.jar clojure_demo.server
```

Up to here we told `vmc` that we are deploying a standalone application, that the runtime is `java` and we provided our startup command.

Let's continue our deployment:

```bash
Application Deployed URL [None]: ${name}.${target-base}
Memory reservation (128M, 256M, 512M, 1G, 2G) [512M]: 512
How many instances? [1]: 1
```

If we are deploying a web application then we need to provide the URL for our app. In our case we let vmc build the URL based on our application name. In this case it will be under `http://clojure-demo.cloudfoundry.com`.

Now let's create and bind one instance of each service type to our app:

```bash
Bind existing services to 'clojure-demo'? [yN]: N
Create services to bind to 'clojure-demo'? [yN]: y
1: blob
2: mongodb
3: mysql
4: postgresql
5: rabbitmq
6: redis
What kind of service?: 1
Specify the name of the service [blob-9dcdc]:
Create another? [yN]: y
1: blob
2: mongodb
3: mysql
4: postgresql
5: rabbitmq
6: redis
What kind of service?: 2
Specify the name of the service [mongodb-cb862]:
Create another? [yN]: y
1: blob
2: mongodb
3: mysql
4: postgresql
5: rabbitmq
6: redis
What kind of service?: 3
Specify the name of the service [mysql-2358b]:
Create another? [yN]: y
1: blob
2: mongodb
3: mysql
4: postgresql
5: rabbitmq
6: redis
What kind of service?: 4
Specify the name of the service [postgresql-de020]:
Create another? [yN]: y
1: blob
2: mongodb
3: mysql
4: postgresql
5: rabbitmq
6: redis
What kind of service?: 5
Specify the name of the service [rabbitmq-3422d]:
Create another? [yN]: y
1: blob
2: mongodb
3: mysql
4: postgresql
5: rabbitmq
6: redis
What kind of service?: 6
Specify the name of the service [redis-29cde]:
Create another? [yN]: N
```

Now we probably want to save this configuration inside a `manyfest.yml` file so we don't need to type all that again the next time we deploy our app from scratch.

```bash
Would you like to save this configuration? [yN]: y
Manifest written to manifest.yml.
```

From now on `vmc` will deploy our app and start it automatically:

```bash
Creating Application: OK
Creating Service [mongodb-cb862]: OK
Binding Service [mongodb-cb862]: OK
Creating Service [mysql-2358b]: OK
Binding Service [mysql-2358b]: OK
Creating Service [postgresql-de020]: OK
Binding Service [postgresql-de020]: OK
Creating Service [blob-9dcdc]: OK
Binding Service [blob-9dcdc]: OK
Creating Service [rabbitmq-3422d]: OK
Binding Service [rabbitmq-3422d]: OK
Creating Service [redis-29cde]: OK
Binding Service [redis-29cde]: OK
Uploading Application:
  Checking for available resources: OK
  Processing resources: OK
  Packing application: OK
  Uploading (5M): OK
Push Status: OK
Staging Application 'clojure-demo': OK
Starting Application 'clojure-demo': OK
```

You can access a demo app [here](http://clojure-demo.cloudfoundry.com/).

## NOTE

At the moment (Sept. 2012) Clojure apps are not officialy suported in Cloud Foundry, but since Java is supported you can still run them.

## License

Copyright (C) 2012 Alvaro Videla <avidela@vmware.com>

See the LICENSE file.
