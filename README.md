[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

# Android core mx #

Es un conjunto de módulos que incluyen utilerías base que permite a los desarrolladores crear aplicaciones robustas para Android implementando el patrón MVP de forma simple.

![Alt text](assets/mvp-diagram.png)

## Cómo utilizarlo ##
__Paso 1.__ En el archivo `build.gradle` del proyecto, en la sección de `repositories` agregar la referencia de `maven { url 'https://jitpack.io' }` como se muestra a continuación:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

__Paso 2.__ Agregar la dependencia de `coremx` en el archivo `build.gradle` de la aplicación/módulo

Gradle previas a 3.0.0

```gradle
   dependencies {
      compile ''
   }
```

Gradle 3.0.0+

```gradle
   dependencies {
      implementation ''
   }
```


### Rx MVP ###
Consiste en una implementación del patrón MVP ( Model View Presenter) utilizando los principios de clean architecture.

Se extiende funcionalidad mediante el uso de RxJava para Android

``` java

```

### SharedPreferences Extensions ###
``` java
SharedPreferencesExtensions
                .builder(getApplicationContext())
                .loggable(BuildConfig.DEBUG)
                .setName("TestApp")
                .setMode(Context.MODE_PRIVATE)
                .build();
```

### Retrofit Extensions ###
``` java
LoggingInterceptor loggerInterceptor = new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();

        RequestInterceptor requestInterceptor =
                new RequestInterceptor(new Connectivity(this));

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggerInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        ServiceClient.builder(client)
                .loggable(BuildConfig.DEBUG)
                .addEndpoint("https://endpoint") // BuildConfig.HOST
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

```

``` java

```
