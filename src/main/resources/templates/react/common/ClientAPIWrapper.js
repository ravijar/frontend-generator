import { createConfiguration, DefaultApi } from '../client_api';

export function createApiClient(token) {
    if (token) {
        const config = createConfiguration({
            authMethods: {
                GoogleOAuth: { accessToken: token },
            },
        });
        return new DefaultApi(config);
    } else {
        const config = createConfiguration();
        return new DefaultApi(config);
    }
}
