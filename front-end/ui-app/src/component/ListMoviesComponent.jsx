import React, {Component} from 'react'
import CourseDataService from '../service/MovieDataService.js';
import {Button, ButtonGroup} from 'reactstrap';
import {Link} from 'react-router-dom';
import HoverRating from "./HoverRating";
import LetterAvatars from "./LetterAvatars";
import BadgeComponent from "./BadgeComponent";
// MUI
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';

class ListMoviesComponent extends Component {
  constructor(props) {
    super(props)
    this.state = {
      movies: [],
      message: null,
      editRole: null,
      name: '',
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
    console.info(JSON.stringify(genres))
    return (
        genres.map((item) => (
          <li key={item.value}>{item.label}</li>
        ))
    );
  }

  render() {
    const columns = [
      { id: 'name', label: 'Name', minWidth: 170 },
      { id: 'genreAsString', label: 'Genre', minWidth: 100},
      { id: 'year', label: 'Year', minWidth: 100 },
      { id: 'rating', label: 'Rating', minWidth: 100, format: (value) => value.toFixed(2) },
      { id: 'country', label: 'Country', minWidth: 100 },
    ];

    const rows = this.state.movies;

    return (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
          <TableContainer sx={{ maxHeight: 440 }}>
            <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  {columns.map((column) => (
                      <TableCell
                          key={column.id}
                          align={column.align}
                          style={{ minWidth: column.minWidth, fontSize: "medium", fontWeight: "bold" }}
                      >
                        {column.label}
                      </TableCell>
                  ))}
                  <TableCell style={{fontSize: "medium", fontWeight: "bold" }}>
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
                                    {column.format && typeof value === 'number'
                                        ? column.format(value)
                                        : value}
                                  </TableCell>
                              );
                            })}
                            <TableCell>
                              <ButtonGroup>
                               <Button color="primary" tag={Link} to={"/movies/" + row.id}>Edit</Button>
                               <Button color="danger" onClick={() => this.remove(row.id)}>Delete</Button>
                             </ButtonGroup>
                            </TableCell>
                          </TableRow>
                      );
                    })}

              </TableBody>
            </Table>
          </TableContainer>
          <TablePagination
              rowsPerPageOptions={[10, 25, 100]}
              component="div"
              count={rows.length}
              rowsPerPage={this.state.rowsPerPage}
              page={this.state.page}
              onPageChange={this.handleChangePage}
              onRowsPerPageChange={this.handleChangeRowsPerPage}
          />
        </Paper>


      // <div className="container">
      //   <h3>List of movies</h3>
      //   <div style={{display: 'flex', justifyContent:'flex-end'}}>
      //     <LetterAvatars name={sessionStorage.authenticatedUser} />
      //   </div>
      //   <div className="container">
      //     <table className="table">
      //       <thead>
      //       <tr>
      //       </tr>
      //       </thead>
      //       <tbody>
      //       <tr>
      //         <td>
      //           <Link to={"/movies/new"}>
      //             <Button color="success">Add</Button>
      //           </Link>
      //         </td>
      //       </tr>
      //       </tbody>
      //     </table>
      //     <table className="table">
      //       <thead>
      //       <tr>
      //         <th>Id</th>
      //         <th>Name</th>
      //         <th>Genre</th>
      //         <th>Year</th>
      //         <th>Rating</th>
      //         <th>Reviews</th>
      //         <th>Operations</th>
      //       </tr>
      //       </thead>
      //       <tbody>
      //       {
      //         this.state.movies.map(
      //           movie =>
      //             <tr key={movie.id}>
      //               <td>{movie.id}</td>
      //               <td>{movie.name}</td>
      //               <td>
      //                 {this.renderGenres(movie.genreList)}
      //               </td>
      //               <td>{movie.year}</td>
      //               <td><HoverRating value={movie.rating}/></td>
      //               <td><Link to={"/login"}><BadgeComponent count={movie.reviews.length}/></Link></td>
      //               <td>
      //                 {sessionStorage.getItem("userRoles")
      //                   .split(',')
      //                   .filter(item => (item === 'ROLE_OTHERS'))
      //                   .length > 0 &&
      //                 <ButtonGroup>
      //                   <Button size="sm" color="primary" tag={Link} to={"/movies/" + movie.id}>Edit</Button>
      //                   <Button size="sm" color="danger" onClick={() => this.remove(movie.id)}>Delete</Button>
      //                 </ButtonGroup>
      //                 }
      //               </td>
      //             </tr>
      //         )
      //       }
      //       </tbody>
      //     </table>
      //   </div>
      // </div>
    );
  }
}

export default ListMoviesComponent
