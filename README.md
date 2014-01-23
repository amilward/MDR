Model Catalogue
===

The Model Catalogue is an open source metadata registry, pathway and forms generator based upon ISO standards.

# Building Model Catalogue

This project is built in Grails, but also has as few front-end dependencies we manage with Bower.

## Prerequisites

To build the project you'll need

1. NodeJS (we're not fussy about the version, as long as it has NPM)
2. Java 7 JDK or newer

The Grails and Gradle environments will be automatically downloaded and installed when you run <code>./gradlew</code> or <code>./grailsw</code>. Handy!

## Building the dependencies

To get up and running quickly, run <code>./gradlew</code> (Unix) or <code>gradlew.bat</code> (Windows) to have the amazing [Gradle](http://www.gradle.org/) set up the dependencies for you.

We haven't (yet) integrated the Grails build into Gradle, so that's another step:

## Running the application

Once you've got the dependencies above the standard Grails commands are you friends, so <code>./grailsw run-app</code> (Again using <code>grailsw.bat</code> on Windows) will run a local instance, and <code>./grailsw test-app</code> will run the test suite.

# License

This project is released under the MIT license (http://opensource.org/licenses/MIT):

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.