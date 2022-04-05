import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios'
import Select from 'react-select'
import {Rating} from "@material-ui/lab";
import { FormControlLabel, Radio, RadioGroup} from "@material-ui/core";

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
      genre: [],
      firstRound:true,
      movieClassification: '',
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSelect= this.handleSelect.bind(this);
    this.updateClassification= this.updateClassification.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      let axiosResponse = await this.findBy(`${this.props.match.params.id}`);
      const movie = axiosResponse.data;
      console.log(JSON.stringify(movie))
      this.setState({item: movie});
      this.setState({
        movieClassification: movie.classification
      });
    }
    let axiosResponse = await this.findAllGenres();
    const genres = axiosResponse.data;
    let options = genres.map(function (genre) {
      return { value: genre.id, label: genre.name };
    })
    this.setState({genres: options})

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
    this.setState({
      firstRound: false
    });
  }

  handleSelect(genre) {
    genre.map(item => {
      this.state.item.genres = genre;
    })
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    if (!item.genreList) {
      item.genreList = this.state.selectedGenres
    }
    item.genreList = this.state.item.genres;
    item.classification = this.state.movieClassification;
    delete item.genres;
    delete item.genre;
    console.info("Item->"+JSON.stringify(item))
    await this.save(item);
    this.props.history.push('/movies');
  }

  updateClassification = (event) => {
    this.setState({
      movieClassification: event.target.value
    });
  };

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Movie' : 'Add Movie'}</h2>;
    if (this.state.firstRound) {
      const genresFromMovie = []
      for (const index in item.genre) {
        genresFromMovie.push(Number(item.genre[index].id))
      }

      if (typeof this.state.genres != "string") {
        for (const index in this.state.genres) {
          if (genresFromMovie.includes(this.state.genres[index].value) && !this.state.selectedGenres.includes(this.state.genres[index].value)) {
            this.state.selectedGenres.push(this.state.genres[index])
          }
        }
      }
    }
    const enumClassification = [
      {id: "G", value: "G-General, suitable for all ages"},
      {id: "PG", value: "PG-Parental Guidance Suggested"},
      {id: "PG13", value: "PG13-Parents Strongly Cautioned"},
      {id: "R", value: "R-Restricted"},
      {id: "NC17", value: "NC17-No Children Under 17"},
      {id: "X", value: "X-Not Suitable For Children"}
    ];
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
            <Label for="duration">Duration in minutes</Label>
            <Input type="text" name="duration" id="duration" value={item.duration || ''}
                   onChange={this.handleChange} autoComplete="duration"/>
          </FormGroup>
          <FormGroup>
            <Label for="country">Country</Label>
            <Input type="text" name="country" id="country" value={item.country || ''}
                   onChange={this.handleChange} autoComplete="country"/>
          </FormGroup>
          <FormGroup>
            <Label for="language">Language</Label>
            <Input type="text" name="language" id="language" value={item.language || ''}
                   onChange={this.handleChange} autoComplete="language"/>
          </FormGroup>
          <label>Classification</label>
          <RadioGroup name="value" value={this.state.movieClassification} onChange={this.updateClassification}>
            {enumClassification
                .map(option => (
                    <FormControlLabel
                        label={option.value}
                        key={option.value}
                        value={option.id}
                        checked={this.state.movieClassification === option.id}
                        control={<Radio color="primary" />}
                    />
                ))}
          </RadioGroup>
          <label>Rating</label>
          <FormGroup>
            <Rating name="rating" value={Math.round(item.rating).toFixed(2) || 1} precision={0.5} max={10}  onChange={this.handleChange}/>
          </FormGroup>
          <FormGroup>
            <Button color={"inherit"} type="submit" disabled={false}>Save</Button>{' '}
            <Button color={"inherit"} tag={Link} disabled={false} to="/movies">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(MovieEdit);