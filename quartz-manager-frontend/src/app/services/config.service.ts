import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';


const WEBJAR_PATH = '/quartz-manager-ui/';

export const CONTEXT_PATH = '/quartz-manager';

export function getHtmlBaseUrl() {
  const baseUrl = getBaseUrl() || '/';
  return environment.production ? getBaseUrl() + WEBJAR_PATH : '/';
}

export function getBaseUrl() {
  if (environment.production) {
    let contextPath: string = window.location.pathname.split('/')[1] || '';
    if (contextPath && ('/' + contextPath + '/') === WEBJAR_PATH) {
      return '';
    }
    if (contextPath) {
      contextPath = '/' + contextPath;
    }
    return contextPath;
  }
  return '';
}

@Injectable()
export class ConfigService {

  private _api_url = getBaseUrl() + `${CONTEXT_PATH}/api`

  private _refresh_token_url = this._api_url + '/refresh';

  private _login_url = this._api_url + '/login';

  private _logout_url = this._api_url + '/logout';

  private _whoami_url = this._api_url + '/whoami';

  private _user_url = this._api_url + '/user';

  private _users_url = this._user_url + '/all';

  get refresh_token_url(): string {
    return this._refresh_token_url;
  }

  get whoami_url(): string {
    return this._whoami_url;
  }

  get users_url(): string {
    return this._users_url;
  }

  get login_url(): string {
    return this._login_url;
  }

  get logout_url(): string {
    return this._logout_url;
  }

}
