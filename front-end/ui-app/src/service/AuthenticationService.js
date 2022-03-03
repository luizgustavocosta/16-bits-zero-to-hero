import axios from 'axios'

const API_URL = 'http://localhost:8080'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
export const USER_PROFILE = 'userProfile'

class AuthenticationService {

  executeBasicAuthenticationService(username, password) {
    return axios.get(`${API_URL}/basicauth`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}})
  }

  executeJwtAuthenticationService(username, password) {
    console.log(username);
    return axios.post(`${API_URL}/authenticate`, {
      username,
      password
    })
  }

  createBasicAuthToken(username, password) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  registerSuccessfulLogin(username, password) {
    //let basicAuthHeader = 'Basic ' +  window.btoa(username + ":" + password)
    sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
    sessionStorage.setItem(USER_PROFILE, "admin")
    this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
  }

  registerSuccessfulLoginForJwt(username, token) {
    sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
    this.setupAxiosInterceptors(this.createJWTToken(token))
  }

  createJWTToken(token) {
    return 'Bearer ' + token
  }


  logout() {
    sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
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