## CICS Transaction Gateway Liberty JCA sample code

### Pre-reqs
* CICS Transaction Gateway v8.1 or later
* WebSphere Liberty profile 8.5.5.3 or later with the JCA feature installed
* Eclipse with WebSphere Development Tools installed

### Configuration
The supplied `server.xml` file shows the required configuration paramters for adding the ECI resource adapter, creating a connection factory and adding the classes supplied with the resource adapter to the classpath of the application. This file can be used as-is or the configuration options can be copied into an existing server configuration.

### Compiling the sample
The sample servlet code can be added to a Web Project. To correct any compilation errors the cicsjee.jar should be added to the build path.

### Reference
More information about working with CICS TG resource adapters in WebSphere Liberty Profile can be found in this [blog post](https://developer.ibm.com/cics/2014/05/06/using-jca-with-the-cics-tg-in-websphere-liberty-profile/).

### Notice
&copy; Copyright IBM Corporation 2014

### License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
