# clojure-demo

This project shows how you can integrate your Clojure apps with [Cloud Foundry](http://cloudfoundry.com).

Here you can find a sample Noir web app that will display the credentials for the Cloud Foundry services that you bind to it.

This project uses [claude](http://github.com/videlalvaro/claude), a library that exposes Cloud Foundry services to your Clojure apps.

## Deploying your Clojure apps to Cloud Foundry

We asume that you are using [leiningen](https://github.com/technomancy/leiningen) for your Clojure projects.

First you will add the latest version of `claude` into your project dependencies:

```clojure
[claude "0.2.1-SNAPSHOT"]
```

Then run `lein deps` to obtain the project dependencies.

Once you have your project set up then you  need to declare your project `main` function:

  :main clojure-demo.server

See this project `project.clj` file for more details.

Then on your main namespace you need to add a `:gen-class` like in this example:

```clojure
(ns clojure-demo.server
  (:gen-class)
  (:require [noir.server :as server]
            [claude.core :as cf]))
```

So far we have a basic clojure project that can be run from a .jar file. Let's create that jar file and deploy it to Cloud Foundry.

```bash
mkdir cf
lein uberjar
cp target/clojure-demo-0.1.0-SNAPSHOT-standalone.jar cf/
cd cf
vmc push
```

## License

Copyright (C) 2012 Alvaro Videla <avidela@vmware.com>

See the LICENSE file.
