import React, {Component} from 'react'
import DataService from '../service/BackendService.js';
import {Link} from 'react-router-dom';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/Button';
import Stack from "@mui/material/Stack";

class MovieListComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            movies: [],
            page: 0,
            rowsPerPage: 10,
        }
        this.refreshMovies = this.refreshMovies.bind(this);
        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({
            page: newPage,
        });
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({
            rowsPerPage: +event.target.value,
            page: 0,
        });
    };

    remove(id) {
        DataService.delete(id)
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
        DataService.retrieveAllMovies()
            .then(
                response => {
                    this.setState({movies: response.data})
                }
            )
    }

    render() {
        const columns = [
            {id: 'name', label: 'Name', minWidth: 170},
            {id: 'genreAsString', label: 'Genre', minWidth: 100},
            {id: 'year', label: 'Year', minWidth: 100},
            {id: 'reviews', label: 'Reviews', minWidth: 50, format: value => value.length},
            {id: 'rating', label: 'Rating', minWidth: 100, format: (value) => value.toFixed(2)},
            {id: 'country', label: 'Country', minWidth: 100},
        ];

        const rows = this.state.movies;

        return (
            <div>
                {sessionStorage.getItem("userRoles")
                    .split(',')
                    .filter(item => (item === 'ROLE_OTHERS'))
                    .length > 0 &&
                <Link to={"/movies/new"}>
                    <Button variant="contained" sx={{mb: 2}} id="addButton">Add</Button>
                </Link>
                }
                <Paper sx={{width: '100%', overflow: 'hidden', mt: '2px'}}>
                    <TableContainer sx={{maxHeight: 600}}>
                        <Table stickyHeader aria-label="sticky table">
                            <TableHead>
                                <TableRow>
                                    {columns.map((column) => (
                                        <TableCell
                                            key={column.id}
                                            align={column.align}
                                            style={{minWidth: column.minWidth, fontSize: "medium", fontWeight: "bold"}}
                                        >
                                            {column.label}
                                        </TableCell>
                                    ))}
                                    <TableCell style={{fontSize: "medium", fontWeight: "bold"}}>
                                        Operations
                                    </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rows
                                    .slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                    .map((row) => {
                                        return (
                                            <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                                                {columns.map((column) => {
                                                    const value = row[column.id];
                                                    return (
                                                        <TableCell key={column.id} align={column.align}>
                                                            {this.renderFormat(column, value)}
                                                        </TableCell>
                                                    );
                                                })}
                                                <TableCell>
                                                    {sessionStorage.getItem("userRoles")
                                                            .split(',')
                                                            .filter(item => (item === 'ROLE_OTHERS'))
                                                            .length > 0 &&
                                                        <ButtonGroup>
                                                            <Stack spacing={2} direction="row">
                                                                <Button variant="contained" component={Link}
                                                                        to={"/movies/" + row.id}>Edit</Button>
                                                                <Button variant="contained"
                                                                        onClick={() => this.remove(row.id)}
                                                                        color={"error"}>Delete</Button>
                                                            </Stack>
                                                        </ButtonGroup>
                                                    }
                                                </TableCell>
                                            </TableRow>
                                        );
                                    })}

                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        sx={{mt: 2}}
                        rowsPerPageOptions={[10, 25, 100]}
                        component="div"
                        count={rows.length}
                        rowsPerPage={this.state.rowsPerPage}
                        page={this.state.page}
                        onPageChange={this.handleChangePage}
                        onRowsPerPageChange={this.handleChangeRowsPerPage}
                    />
                </Paper>
            </div>
        );
    }

    renderFormat(column, value) {
        if (column.format && typeof value === 'number')
            return column.format(value)
        else if (column.format && typeof value === 'object')
            return Array.from(value).length
        else
            return value
    }
}

export default MovieListComponent
