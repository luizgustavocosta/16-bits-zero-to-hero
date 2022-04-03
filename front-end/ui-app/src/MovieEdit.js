import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios'
import Select from 'react-select'
class MovieEdit extends Component {

  emptyItem = {
    name: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      genres: '',
      selectedGenres: [],
      newGenres:[],
      genre: [],
      year: 0,
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSelect= this.handleSelect.bind(this);
    this.handleYearChange = this.handleYearChange.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      let axiosResponse = await this.findBy(`${this.props.match.params.id}`);
      const movie = axiosResponse.data;
      this.setState({item: movie});
    }
    let axiosResponse = await this.findAllGenres();
    const genres = axiosResponse.data;
    let options = genres.map(function (genre) {
        return { value: genre.id, label: genre.name };
      })
    this.setState({genres: options})
    this.setState(
        {year:this.state.item.year}
    )
    console.log("This.state.year["+this.state.year+"]")
    console.log("movie.year["+this.state.item.year+"]")
  }

  findBy(id) {
    return axios.get(`http://localhost:8080/movies/${id}`,);
  }

  findAllGenres() {
    return axios.get(`http://localhost:8080/api/v1/genres`,);
  }

  save(item) {
    return axios.put(`http://localhost:8080/movies`, item);
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  handleSelect(genre) {
    genre.map(item => {
     this.state.item.genres = genre;
    })
  }

  handleYearChange = (event) => {
    this.setState({
      year: event.target.value,
    });
  };

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    if (!item.genreList) {
      item.genreList = this.state.selectedGenres
    }
    item.genreList = this.state.item.genres;
    delete item.genres;
    delete item.genre;
    console.info("Item->"+JSON.stringify(item))
    await this.save(item);
    this.props.history.push('/movies');
  }

  render() {


    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Movie' : 'Add Movie'}</h2>;
    const genresFromMovie = []
    for (const index in item.genre) {
      genresFromMovie.push(Number(item.genre[index].id))
    }
    console.info("this.state.genres["+this.state.genres+"]")

    if (typeof this.state.genres != "string") {
      for (const index in this.state.genres) {
        if (genresFromMovie.includes(this.state.genres[index].value) && !this.state.selectedGenres.includes(this.state.genres[index].value)) {
          this.state.selectedGenres.push(this.state.genres[index])
        }
      }
    }

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <Input type="hidden" name="id" id="id" value={item.id}/>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Select
                closeMenuOnSelect={true}
                defaultValue={this.state.selectedGenres}
                isMulti
                onChange={this.handleSelect}
                options={this.state.genres}
            />
          </FormGroup>
          <FormGroup>
            <Label for="year">Year</Label>
            <Input type="text" name="year" id="year" value={item.year || ''}
                   onChange={this.handleChange} autoComplete="year"/>
          </FormGroup>
          <FormGroup>
            <Button color={"inherit"} type="submit">Save</Button>{' '}
            <Button color={"inherit"} tag={Link} to="/movies">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(MovieEdit);