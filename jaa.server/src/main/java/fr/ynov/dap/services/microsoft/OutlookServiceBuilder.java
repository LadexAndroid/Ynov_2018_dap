package fr.ynov.dap.services.microsoft;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * OutlookServiceBuilder, used to create the OutlookService.
 */
public final class OutlookServiceBuilder {
    /**
     * private constructor because this is a utility class.
     */
    private OutlookServiceBuilder() {
    }

    /**
     * Create the Outlook service.
     * @param accessToken access token.
     * @param userEmail email of the user.
     * @return Outlook service.
     */
    public static OutlookService getOutlookService(final String accessToken, final String userEmail) {
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(final Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Builder builder = original.newBuilder().header("User-Agent", "java-tutorial")
                        .header("client-request-id", UUID.randomUUID().toString())
                        .header("return-client-request-id", "true")
                        .header("Authorization", String.format("Bearer %s", accessToken))
                        .method(original.method(), original.body());

                Request request = builder.build();
                return chain.proceed(request);
            }
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://graph.microsoft.com").client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        return retrofit.create(OutlookService.class);
    }
}
