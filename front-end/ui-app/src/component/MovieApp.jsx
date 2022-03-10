import React, {Component} from 'react';
import ListMoviesComponent from './ListMoviesComponent';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import LoginComponent from './LoginComponent';
import LogoutComponent from './LogoutComponent';
import MenuComponent from './MenuComponent';
import AuthenticatedRoute from './AuthenticatedRoute';
import MovieEdit from "../MovieEdit";
import NewUser from "../NewUser";

class MovieApp extends Component {
  render() {
    return (
      <>
        <Router>
          <>
            <MenuComponent/>
            <Switch>
              <Route path="/" exact component={LoginComponent}/>
              <Route path="/login" exact component={LoginComponent}/>
              <Route path="/newUser" exact component={NewUser}/>
              <AuthenticatedRoute path='/movies/:id' component={MovieEdit}/>
              <AuthenticatedRoute path="/logout" exact component={LogoutComponent}/>
              <AuthenticatedRoute path="/movies" exact component={ListMoviesComponent}/>
            </Switch>
          </>
        </Router>
      </>
    )
  }
}

export default MovieApp