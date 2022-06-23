import axios from 'axios'

const API_URL = process.env.REACT_APP_BACKEND_SERVER_URL

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
export const USER_PROFILE = 'userProfile'
export const USER_ROLES = 'userRoles'

class AuthenticationService {

  executeBasicAuthenticationService(username, password) {
    return axios.get(`${API_URL}/basicauth`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}})
  }

  registerSuccessfulLogin(username, password, roles) {
    sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
    sessionStorage.setItem(USER_PROFILE, "admin")
    sessionStorage.setItem(USER_ROLES, roles)
    this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
  }

  createBasicAuthToken(username, password) {
    //https://developer.mozilla.org/en-US/docs/Web/API/btoa
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  logout() {
    sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    // FIXME - Call the backend endpoint
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    return user !== null;

  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }

  setupAxiosInterceptors(token) {
    axios.interceptors.request.use(
      (config) => {
        if (this.isUserLoggedIn()) {
          config.headers.authorization = token
        }
        return config
      }
    )
  }
}

export default new AuthenticationService()