import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Container, Form, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios'
import Select from 'react-select'
import {Rating} from "@material-ui/lab";
import {FormControl, FormControlLabel, FormGroup, Radio, RadioGroup, TextField} from "@material-ui/core";
import ButtonGroup from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

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
      movie: [],
      name: '',
      year: '',
      duration: '',
      language: '',
      country: '',
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
      this.setState({item: movie});
      this.setState({
        movieClassification: movie.classification
      });
      this.setState({movie: movie});
      this.setState({name: movie.name});
      this.setState({duration: movie.duration});
      this.setState({country: movie.country});
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

  handleFormChange = (event, name) => {
    this.setState({[name] : event.target.value})
  }

  handleSelect(genre) {
    genre.map(item => {
      this.state.item.genres = genre;
    })
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    console.log("HandleSubmit["+JSON.stringify(this.state)+"]")
    if (!item.genreList) {
      item.genreList = this.state.selectedGenres
    }
    item.genreList = this.state.item.genres;
    item.classification = this.state.movieClassification;
    delete item.genres;
    delete item.genre;
    console.info("Item->"+JSON.stringify(this.state.movie))
    await this.save(this.state);
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
      console.info("RENDER["+JSON.stringify(this.state.movie)+"]")
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
          <Box
              component="form"
              sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
              }}
              noValidate
              autoComplete="off"
          >
            <TextField id="outlined-basic" label="Name" variant="outlined" value={this.state.name || ''} onChange={event => this.handleFormChange(event, "name")}/>
            <TextField id="outlined-basic" label="Year" variant="outlined" value={this.state.year || ''} onChange={event => this.handleFormChange(event, "year")}/>
            <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined">
              <TextField id="outlined-basic" label="Duration" variant="outlined" value={this.state.duration || ''} onChange={event => this.handleFormChange(event, "duration")}/>
            </FormControl>
          </Box>
          <Box
              component="form"
              sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
              }}
              noValidate
              autoComplete="off"
          >
            <TextField id="outlined-basic" label="Country" variant="outlined" value={this.state.country || ''} onChange={event => this.handleFormChange(event, "country")}/>
            <TextField id="outlined-basic" label="Language" variant="outlined" value={this.state.language || ''} onChange={event => this.handleFormChange(event, "language")}/>
          </Box>
          <FormGroup style={{marginLeft:8}} aria-label={"Genres"}>
            <Label>Genres</Label>
            <Select
                closeMenuOnSelect={true}
                defaultValue={this.state.selectedGenres}
                isMulti
                onChange={this.handleSelect}
                options={this.state.genres}
            />
          </FormGroup>
          <Box
              component="form"
              sx={{
                '& .MuiTextField-root': { mr: 8, width: '25ch' },
              }}
              noValidate
              autoComplete="off"
          >
            <label style={{marginLeft:8}}>Classification</label>
            <RadioGroup name="value" value={this.state.movieClassification} onChange={this.updateClassification}>
              {enumClassification
                  .map(option => (
                      <FormControlLabel
                          style={{marginLeft:8}}
                          label={option.value}
                          key={option.value}
                          value={option.id}
                          checked={this.state.movieClassification === option.id}
                          control={<Radio color="primary" />}
                      />
                  ))}
            </RadioGroup>
          </Box>
          <Box
              component="form"
              sx={{
                '& .MuiTextField-root': { mr: 8, width: '25ch' },
              }}
              noValidate
              autoComplete="off"
          >
            <label style={{marginLeft:8}}>Rating</label>
            <FormGroup style={{marginLeft:8}}>
              <Rating name="rating" value={Math.round(this.state.rating).toFixed(2) || 1}
                      precision={0.5} max={10}
                      onChange={event => this.handleFormChange(event, "rating")}/>
            </FormGroup>
          </Box>
          <ButtonGroup>
            <Stack spacing={2} direction="row">
              <Button variant="contained" type={"submit"}>Save</Button>
              <Button variant="contained" component={Link} to={"/movies"}>Cancel</Button>
            </Stack>
          </ButtonGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(MovieEdit);