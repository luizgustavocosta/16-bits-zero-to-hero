import React, {Component} from 'react';
import './App.css';
import MovieApp from './component/MovieApp.jsx';

class App extends Component {
  render() {
    return (
      <div className="container">
        <MovieApp/>
      </div>
    );
  }
}

export default App;