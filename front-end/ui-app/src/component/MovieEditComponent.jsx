import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Rating} from "@material-ui/lab";
import {
    FormControl,
    FormControlLabel,
    FormGroup,
    MenuItem,
    TextField
} from "@material-ui/core";
import ButtonGroup from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import Radio from "@mui/material/Radio";
import Checkbox from "@mui/material/Checkbox";
import Select from "@mui/material/Select";
import BackendDataService from "../service/BackendService.js";
import applicationConfig from './../application.json'

class MovieEditComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            classification: '',
            movie: [],
            name: '',
            year: '',
            duration: '',
            language: '',
            country: '',
            genreOptions: [],
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.handleRadio = this.handleRadio.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            let axiosResponse = await this.findBy(`${this.props.match.params.id}`);
            const movie = axiosResponse.data;
            this.setState({movie: movie});
        } else {
            this.state.movie.genreIds = []
        }

        let axiosResponse = await this.findAllGenres();
        const genres = axiosResponse.data;
        let options = genres.map(function (genre) {
            return {value: genre.id, label: genre.name};
        })
        this.setState({genres: options})

    }

    findBy(id) {
        return BackendDataService.findBy(id);
    }

    findAllGenres() {
        return BackendDataService.findAllGenres();
    }

    save(movie) {
        return BackendDataService.save(movie);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleFormChange = (event, name) => {
        let movieData = {...this.state.movie}
        movieData[name] = event.target.value
        this.setState({
            movie: movieData
        })
    }

    handleSelect(genre) {
        const value = Number(genre.target.value);
        const current = this.state.movie.genreIds
        if (this.state.movie.genreIds !== undefined && this.state.movie.genreIds.includes(value)) {
            delete current[current.indexOf(value)]
        } else {
            current.push(value)
        }
        let movieData = {...this.state.movie}
        movieData.genreIds = current;
        this.setState({
            movie: movieData
        })
    }

    handleRadio(classification) {
        let value = classification.target.defaultValue;
        this.setState({classification: value,})
    }

    async handleSubmit(event) {
        event.preventDefault();
        console.info("Movie sent to backend->" + JSON.stringify(this.state.movie))
        await this.save(this.state.movie);
        this.props.history.push(applicationConfig.MOVIE_SERVICE);
    }

    render() {
        const titleGenres = "Genres"
        const titleClassification = "Classification"
        const title = <h2>{this.state.movie.id ? 'Editing' : 'Add'}</h2>;
        const enumClassification = [
            {id: "G", value: "G-General, suitable for all ages"},
            {id: "PG", value: "PG-Parental Guidance Suggested"},
            {id: "PG13", value: "PG13-Parents Strongly Cautioned"},
            {id: "R", value: "R-Restricted"},
            {id: "NC17", value: "NC17-No Children Under 17"},
            {id: "X", value: "X-Not Suitable For Children"}
        ];
        const enumCountry = [
            {id: "Brazil", value: "Brazil", icon: `https://flagcdn.com/w20/br.png`},
            {id: "China", value: "China", icon: `https://flagcdn.com/w20/cn.png`},
            {id: "France", value: "France", icon: `https://flagcdn.com/w20/fr.png`},
            {id: "Spain", value: "Spain", icon: `https://flagcdn.com/w20/es.png`},
            {id: "South Korea", value: "South Korea", icon: `https://flagcdn.com/w20/kr.png`},
            {id: "United Kingdom", value: "United Kingdom", icon: `https://flagcdn.com/w20/gb.png`},
            {id: "United States of America", value: "United States of America", icon: `https://flagcdn.com/w20/us.png`},
        ]
        const enumGenres = [
            {id: 1, name: "Action"},
            {id: 2, name: "Adventure"},
            {id: 3, name: "Animation"},
            {id: 4, name: "Comedy"},
            {id: 5, name: "Crime"},
            {id: 6, name: "Drama"},
            {id: 7, name: "Experimental"},
            {id: 8, name: "Fantasy"},
            {id: 9, name: "Historical"},
            {id: 10, name: "Horror"},
            {id: 11, name: "Romance"},
            {id: 12, name: "Science Fiction"},
            {id: 13, name: "Thriller"},
            {id: 14, name: "Western"},
            {id: 15, name: "Other"},
        ]
        return <div>
            {title}
            <TextField id="outlined-basic" name="id" hidden={true} variant="outlined"
                       value={this.state.movie.id || ''}/>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': {m: 1, width: '25ch', mb: 5},
                }}
                noValidate
                autoComplete="off"
            >
                <TextField id="outlined-basic" label="Name" variant="outlined" value={this.state.movie.name || ''}
                           onChange={event => this.handleFormChange(event, "name")}/>
            </Box>
            <label style={{marginLeft: 8}}><b>{titleGenres}</b></label>
            <Box
                component="form"
                sx={{'& .MuiTextField-root': {mr: 8}}}>
                {
                    enumGenres
                        .map(option => (
                            <FormControlLabel
                                variant="standard"
                                style={{marginLeft: 8, marginBottom: 0}}
                                label={option.name}
                                key={option.name}
                                value={option.id}
                                checked={
                                    this.state.movie.genreIds !== null && this.state.movie.genreIds !== undefined && Object.values(this.state.movie.genreIds).includes(Number(option.id))
                                }
                                control={<Checkbox/>}
                                onChange={this.handleSelect}
                            />
                        ))}
            </Box>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': {m: 1, width: '25ch', mt: 5},
                }}
                noValidate
                autoComplete="off"
            >
                <TextField id="outlined-basic" label="Year" variant="outlined" value={this.state.movie.year || ''}
                           onChange={event => this.handleFormChange(event, "year")}/>
                <FormControl sx={{m: 1, width: '25ch'}} variant="outlined">
                    <TextField id="outlined-basic" label="Duration" variant="outlined"
                               value={this.state.movie.duration || ''}
                               onChange={event => this.handleFormChange(event, "duration")}/>
                </FormControl>
                <TextField id="outlined-basic" label="Language" variant="outlined"
                           value={this.state.movie.language || ''}
                           onChange={event => this.handleFormChange(event, "language")}/>
            </Box>
            <Box>
                <Select
                    sx={{ml: 1, maxWidth: 790}}
                    labelId="movie-country"
                    id="demo-simple-select-autowidth"
                    value={this.state.movie.country || ''}
                    onChange={event => this.handleFormChange(event, "country")}
                    fullWidth={true}
                    label="Country"
                >
                    {enumCountry.map(option => (
                        <MenuItem value={option.id}>
                            <img
                                loading="lazy"
                                width="20"
                                alt={option.id}
                                src={option.icon}
                            />&nbsp;{option.value}
                        </MenuItem>
                    ))}
                </Select>
            </Box>
            <label style={{marginLeft: 8, marginTop: 8}}><b>{titleClassification}</b></label>
            <Box
                component="form"
                sx={{'& .MuiTextField-root': {mr: 8}}}>
                {enumClassification
                    .map(option => (
                        <FormControlLabel
                            style={{marginLeft: 8}}
                            label={option.value}
                            key={option.value}
                            value={option.id}
                            checked={this.state.movie.classification === option.id}
                            control={<Radio color="primary"/>}
                            onChange={event => this.handleFormChange(event, "classification")}
                        />
                    ))}
            </Box>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': {mr: 8, width: '25ch'},
                }}
                noValidate
                autoComplete="off"
            >
                <label style={{marginLeft: 8}}>Rating</label>
                <FormGroup style={{marginLeft: 8}}>
                    <Rating name="rating" value={Math.round(this.state.movie.rating).toFixed(2) || 1}
                            precision={0.5} max={10}
                            onChange={event => this.handleFormChange(event, "rating")}/>
                </FormGroup>
            </Box>
            <ButtonGroup aria-label="sticky table">
                <Stack spacing={2} direction="row" ml={1} mt={5}>
                    <Button name="Save" variant="contained" onClick={this.handleSubmit} type={"submit"} title="Save">Save</Button>
                    <Button name="Cancel" component={Link} to={"/movies"} variant="outlined" title="Cancel">Cancel</Button>
                </Stack>
            </ButtonGroup>
        </div>
    }
}

export default withRouter(MovieEditComponent);