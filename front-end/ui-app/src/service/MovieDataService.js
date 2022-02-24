import axios from 'axios'

const USER = 'in28minutes'
const PASSWORD = 'dummy'
const COURSE_API_URL = 'http://localhost:8080'
// const MOVIE_API_URL = `${COURSE_API_URL}/movies/${USER}`
const MOVIE_API_URL = `${COURSE_API_URL}/movies`

class MovieDataService {

    retrieveAllMovies(name) {
        // return axios.get(`${MOVIE_API_URL}/courses`,
        return axios.get(`${MOVIE_API_URL}`,
            //{ headers: { authorization: 'Basic ' + window.btoa(INSTRUCTOR + ":" + PASSWORD) } }
        );
    }
}

export default new MovieDataService()
