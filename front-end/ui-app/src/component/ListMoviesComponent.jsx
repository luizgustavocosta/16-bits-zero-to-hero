import React, {Component} from 'react'
import CourseDataService from '../service/MovieDataService.js';
import {Button, ButtonGroup} from 'reactstrap';
import {Link} from 'react-router-dom';
import HoverRating from "./HoverRating";
import LetterAvatars from "./LetterAvatars";
import BadgeComponent from "./BadgeComponent";

class ListMoviesComponent extends Component {
  constructor(props) {
    super(props)
    this.state = {
      movies: [],
      message: null,
      editRole: null,
      name: 'LG'
    }
    this.refreshMovies = this.refreshMovies.bind(this)
  }

  remove(id) {
    CourseDataService.delete(id)
      .then(
        response => {
          this.refreshMovies();
        }
      )
  }

  componentDidMount() {
    this.refreshMovies();
  }

  refreshMovies() {
    CourseDataService.retrieveAllMovies()
      .then(
        response => {
          this.setState({movies: response.data})
        }
      )
  }

  renderGenres(genres) {
    return (
        genres.map((item) => (
          <li key={item.id}>{item.name}</li>
        ))
    );
  }

  render() {
    const fixme = {
      countOfReviews: '3',
      rating: Math.floor(Math.random() * (5 - 1)) + 1
    };
    return (
      <div className="container">
        <h3>List of movies</h3>
        <LetterAvatars>LG</LetterAvatars>
        <div className="container">
          <table className="table">
            <thead>
            <tr>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>
                <Link to={"/movies/new"}>
                  <Button color="success">Add</Button>
                </Link>
              </td>
            </tr>
            </tbody>
          </table>
          <table className="table">
            <thead>
            <tr>
              <th>Id</th>
              <th>Name</th>
              <th>Genre</th>
              <th>Year</th>
              <th>Rating</th>
              <th>Reviews</th>
              <th>Operations</th>
            </tr>
            </thead>
            <tbody>
            {
              this.state.movies.map(
                movie =>
                  <tr key={movie.id}>
                    <td>{movie.id}</td>
                    <td>{movie.name}</td>
                    <td>
                      {this.renderGenres(movie.genre)}
                    </td>
                    <td>{movie.year}</td>
                    <td><HoverRating value={fixme.rating}/></td>
                    <td><Link to={"/login"}><BadgeComponent count={fixme.countOfReviews}/></Link></td>
                    <td>
                      {sessionStorage.getItem("userRoles")
                        .split(',')
                        .filter(item => (item === 'ROLE_OTHERS'))
                        .length > 0 &&
                      <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/movies/" + movie.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(movie.id)}>Delete</Button>
                      </ButtonGroup>
                      }
                    </td>
                  </tr>
              )
            }
            </tbody>
          </table>
        </div>
      </div>
    )
  }
}

export default ListMoviesComponent
