import axios from 'axios'

const HOST = 'http://localhost:8080'
const MOVIE_API_URL = `${HOST}/movies`

class MovieDataService {

    retrieveAllMovies(name) {
        let promise = axios.get(`${MOVIE_API_URL}`,);
        console.log(promise)
        return promise;
    }

    delete(id) {
        return axios.delete(`${MOVIE_API_URL}/${id}`,);
    }
}

export default new MovieDataService()
