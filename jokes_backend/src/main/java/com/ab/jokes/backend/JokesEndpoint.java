/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.ab.jokes.backend;

import com.example.JokeTeller;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


@Api(
  name = "jokesApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.jokes.ab.com",
    ownerName = "backend.jokes.ab.com",
    packagePath=""
  )
)
public class JokesEndpoint {


    @ApiMethod(name = "getJoke")
    public JokeBean getJoke() {
        JokeBean response = new JokeBean();
        JokeTeller jokeTeller=new JokeTeller();
        response.setJoke(jokeTeller.getJoke());
        return response;
    }

}
