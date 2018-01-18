[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

# Android Core MX #

Es un conjunto de módulos que incluyen utilerías base que permite a los desarrolladores crear aplicaciones robustas para Android implementando el patrón MVP de forma simple.

### Diagrama general ###
![Alt_text](assets/coregeneral.png)

### Diagrama de arquitectura ###
![Alt_text](assets/corearchitecture.png)

### Diagrama ###
![Alt text](assets/mvpdiagram.png)

#### Uso ####
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
Consiste en una implementación del patrón MVP (Model View Presenter) utilizando los principios de clean architecture.

Se extiende funcionalidad mediante el uso de RxJava para Android


#### Uso ####

Cada RxMvp vista debe extender de `mx.gigigo.core.rxmvp.View`

``` java
public interface View { }
```

Cada RxMvp presenter debe extender de `mx.gigigo.core.rxmvp.Presenter` y debe recibir un parámetro (V) que extienda de `mx.gigigo.core.rxmvp.View` 

``` java
public interface Presenter<V extends View> {

    void attachView(V view);

    void detachView();

    void resume();

    void pause();

    void destroy();

    void handleError(Throwable exception);
}
```


Butter Knife for binding views en Activity/Fragment base.

#### Uso ####

Activity
``` java
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MvpProjectNameActivity<V extends View, P extends Presenter<V>>
        extends MvpActivity<V, P> {

    private Unbinder unbinder;

    @Override
    protected void onBindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onUnbindView() {
        if(null != unbinder) unbinder.unbind();
    }
}
```

Fragment
``` java
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MvpProjectNameFragment<V extends View, P extends Presenter<V>>
        extends MvpFragment<V, P> {

    private Unbinder unbinder;

    @Override
    protected void onBindView(android.view.View root) {
        unbinder = ButterKnife.bind(this, root);
    }

    @Override
    protected void onUnbindView() {
        if(null != unbinder) unbinder.unbind();
    }
}
```

__NOTA:__

Cuando el Activity/Fragment no requiera de una implementación de MVP, estos deberán extender de `mx.gigigo.core.rxmvp.BaseActivity` y `mx.gigigo.core.rxmvp.BaseFragment` respectivamente 

### SharedPreferences Extensions ###

Consiste en una extensión de SharedPreferences propias del SDK para simplificar su uso

#### Configuración inicial ####

``` java
SharedPreferencesExtensions
                .builder(context)
                .loggable(BuildConfig.DEBUG)
                .setName("name")
                .setMode(Context.MODE_PRIVATE)
                .build();
```

#### Uso ####

Almacenar cualquier tipo (primitivos, colecciones, objetos ...)
``` java
SharedPreferencesExtensions
                .put(key, class type, value, replace if exist // * optional, default true);
```

Obtener el valor original acorde al tipo (primitivos, colecciones, objetos ...)
``` java
value = SharedPreferencesExtensions
                    .get(key, class type, default value // * optional);
```

Eliminar valores no necesarios
``` java
SharedPreferencesExtensions.delete(key);
```


### Retrofit Extensions ###

Consiste en una extensión de retrofit para simplificar su uso


#### Configuración inicial ####

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

        
        NetworkRequestInterceptor requestInterceptor =
                new DefaultNetworkRequestInterceptor(new DefaultNetwork(context));
                
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggerInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        ServiceClient.builder(client)
                .addEndpoint(endpoint) // puede agreger multiples endpoints
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
                
        ...
        
        
    private class DefaultNetworkRequestInterceptor
            extends NetworkRequestInterceptor {

        public DefaultNetworkRequestInterceptor(Network network) {
            super(network);
        }

        @Override
        protected Response interceptResponse(Chain chain) throws IOException {
            return chain.proceed(chain.request());
        }
    }

```

#### Uso ####

Crear una instancia del servicio (REST API methods) mediante un factory
``` java
ServiceClient serviceClient = ServiceClient.getDefault();

ServiceClientFactory.createService(service client, class type);
```


### Recycler Extensions ###

Consiste en una extensión de `RecyclerView.Adapter` y `RecyclerView.ViewHolder` para simplificar su uso y reducir la generación de código

### View Holder Adapter ###
Cada ViewHolder de RecyclerView deberá extender de `mx.gigigo.core.recyclerextensions.ViewHolderAdapter`
#### Uso ####
``` java
public class CustomViewHolder
        extends ViewHolderAdapter<data model> {

    TextView textViewName;

    public ListUsersViewHolder(View itemView) {
        super(itemView);
        
        textViewName = itemView.findViewById(R.id.text_view_name)
    }

    @Override
    public void onBindViewHolder(data model item) {
        super.onBindViewHolder(item);

        textViewName.setText("value");
    }
}

....

public class CustomAdapter
        extends RecyclerAdapter<data model> {

    @Override
    public ViewHolderAdapter<data model> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, R.layout.item_user);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }
    
```

### Recycler Adapter ###
Cada adapter de RecyclerView deberá extender de `mx.gigigo.core.recyclerextensions.RecyclerAdapter`

#### Uso ####
``` java
public class CustomAdapter
        extends RecyclerAdapter<data model> {

    @Override
    public ViewHolderAdapter<data model> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, R.layout.item_user);
        ViewHolderAdapter viewHolder = new ViewHolderAdapter(view);
        return viewHolder;
    }
}

....

CustomAdapter adapter = new CustomAdapter();

...

recyclerView.setAdapter(adapter);

```

Incluye métodos como:

* isEmpty()
* add(T item)
* addRange(Iterable<T> items)
* addRange(T... items)
* set(Iterable<T> items)
* set(T... items)
* update(T item)
* remove(T item)
* remove(int index)
* where(IPredicate<T> predicate)
* clear()
* items()

### Recycler Header and Footer Adapter ###
Cada adapter de RecyclerView con funcionalidad de Header y/o Footer deberá extender de `mx.gigigo.core.recyclerextensions.RecyclerHeaderFooterAdapter`

#### Uso ####
``` java
public class CustomHeaderFooterAdapter
        extends RecyclerHeaderFooterAdapter<data model, ViewHolderAdapter<data model>> {

    @Override
    public ViewHolderAdapter<data model> onCreateViewHolderHeaderFooter(ViewGroup parent, int viewType) {
        View view = getView(parent, R.layout.item_user);
        ViewHolderAdapter viewHolder = new ViewHolderAdapter(view);
        return viewHolder;
    }
}

....

CustomHeaderFooterAdapter adapter = new CustomHeaderFooterAdapter();

...

recyclerView.setAdapter(adapter);

adapter.setHeaderView(recyclerView, R.layout.template_header);
adapter.setFooterView(recyclerView, R.layout.template_footer);

```

### Eventos de BaseActivity y BaseFragment ###

``` java
// region ProjectNameActivity Methods

@Override
protected int getLayoutId() {
    return R.layout.layout_id;
}

@Override
protected void onRestoreExtras(Bundle arguments) {
    super.onRestoreExtras(arguments);
}

@Override
protected void onInitializeMembers() {
    ...
}

@Override
protected void onInitializeUIComponents() {
    ...
}

@Override
protected Presenter createPresenter() {
    ...
}

// endregion ProjectNameActivity Methods

```

##### Uso ####

Se agregaron los siguientes métodos para agrupar y estandarizar las inicializaciones de objetos y 
referencias de Views y a continuación se explica su uso: 

* `onRestoreExtras(Bundle arguments)`: Se utiliza para extraer los datos existentes en el `Bundle` al 
crear una nueva instancia de un Activity o un Fragment, este objeto se verifica en el evento 
`onCreate` del `BaseActivity` y `BaseFragment`, si en la verificación el `Bundle` es `null` no se 
invocará este método. Esta llamada ocurre antes del `onInitializeMembers` y `onInitializeUIComponents`.
* `onInitializeMembers`: Aquí se inicializarán todas las variables y objetos de la clase que no 
dependan directamente de la existencia de alguna referencia de la vista.
* `onInitializeUIComponents`: Aquí se inicializarán las referencias de componentes de vistas.

### Estandarización de estructura en Activity y Fragment ###
``` java
public class MainActivity
        extends ProjectNameActivity {
    
    // region Constants
    
    private static final String PAGE_NUMBER_ARG = "page_number_arg";
    
    // endregion Constants
    
    // region UI Components
    
    @BindView(R.id.viewpager_sections)
    ViewPager sectionsViewPager;
    
    // endregion UI Components
    
    // region Members
    
    private HomePagerAdapter homePagerAdapter;
    
    // endregion Members
    
    // region Activity Lifecycle
    
    @Override
    protected void onResume() {
        super.onResume();
        ...
    }
        
    // endregion Activity Lifecycle
    
    // region ProjectNameActivity Methods
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    ...
    
    // endregion ProjectNameActivity Methods
    
    // region View Implementation
    // endregion View Implementation
    
    // region ButterKnife View Events
    
    @OnClick(R.id.image_view_avatar)
    public void onAvatarButtonClick() {
        ...
    }
    
    // endregion ButterKnife View Events
    
    // region Public Methods
    // endregion Public Methods
    
    // region Private Methods
    
    private List<Fragment> createFragmentPages(int number) {
        ...
    }
    
    // endregion Private Methods
}
```

``` java

```
