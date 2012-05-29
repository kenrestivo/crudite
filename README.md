# crudite

A library for composing CRUD forms in Noir.

## Why?

For every model and view, I had pages of boilerplate code to handle various GET's POST's, and the like. It was getting crazy insane, and fragile, especially once it came time to make any changes. 

Generated boilerplate CRUD is common in many frameworks, but seems out of place in Clojure. We should be able to compose forms using hash-maps and functions, instead of generating boilerplate or copy-pasting stuff. This is my attempt at solving that problem.

## Status

As of this first release, Crudite creates the GET/POST actions, and some of the page shell around those. It works, and is right now, as of 10 minutes ago, used in production.

However, there is a lot to do still and a great deal I want to add.

## "Install"

With Lein, add to your project.clj:

```clojure
[crudite "0.1.0"]
```

## Usage

In a Noir view, create a map of your settings, then call make-forms on it. 

The full set of settings and documentation for them is in src/crudite/settings.clj, but here's a bare minimum to just give you a skeleton:

```clojure

(use '[crudite.core :only [make-forms]])

(def form-settings {:list-url "/test-things"
                :view-url "/test-thing/"
                :title "Test Things"
                :title-singular "Test Thing"
}

(make-forms form-settings)

```

The next time you reload that view's namespace (or refresh the page in Noir), crudite will generate the following routes for you automatically:

```clojure
 :GET--test-thing-->id
 :GET--test-thing-->id--edit
 :GET--test-thing--add
 :GET--test-things
 :POST--test-thing-->_id--update
 :POST--test-thing--add
```
Translated from Compojure-speak to English, that's:
 
```
 /test-things
 /test-thing/:id
 /test-thing/:id/edit
 /test-thing/:_id/update (post)
 /test-thing/add
 /test-thing/add (post)
```

In the HTML generated by the routes, Crudite will also add Hiccup code for buttons to take you from the view page to the edit page, and from the list page to the add page.

## TODO:

There's a TODO file in git, but the major next steps are the ability to compose the actual forms (wrapping around validation rules and defpartial for the fields), and also a test harness, which will probably be a Noir server inside tests.

## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
