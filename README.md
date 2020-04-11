# duct.database.algolia

Integrant methods for connecting to a Algolia index.

## Usage

### Installation

To install, add the following to your project dependencies:

`[hden/duct.database.algolia "0.1.0-SNAPSHOT"]`

### Usage

This library provides two things: a `Boundary` record that holds a database spec, and a multimethod for :duct.database/algolia that initiates a database spec into the `Boundary`.

```clojure
{:duct.database/algolia {:app-id  "APP_ID"
                         :api-key "API_KEY"
                         :index   "INDEX_NAME"}}
```

When you write functions against the database, consider using a protocol and extending the Boundary record. This will allow you to easily mock or stub out the database using a tool like [Shrubbery](https://github.com/bguthrie/shrubbery).

## License

Copyright © 2020 Haokang Den

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.
