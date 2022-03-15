import * as React from 'react';
import { useState } from 'react';

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import {Component} from "react";
import { Redirect } from 'react-router-dom';

class NewUserComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open : false
       }
        this.handleClickOpen = this.handleClickOpen.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.handleAgree = this.handleAgree.bind(this)
    }

    handleClickOpen = () => {
        this.setState({open:true})
    };

    handleAgree = () => {
        console.log("hit")
        this.setState({open:true})
    };

    handleClose = () => {
        this.state.open = true;
        if (this.state.open) {
            this.props.history.push('/movies');
        }
    };

    render() {
        return (
            <div>
                <Box
                    component="form"
                    sx={{
                        '& > :not(style)': {m: 1, width: '25ch'},
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <TextField id="outlined-basic" label="Outlined" variant="outlined"/>
                    <TextField id="filled-basic" label="Filled" variant="filled"/>
                    <TextField id="standard-basic" label="Standard" variant="standard"/>
                </Box>
                <Button variant="contained" onClick={this.handleAgree}>
                    Save
                </Button>
                <Dialog
                    open={this.state.open}
                    keepMounted
                    //onClose={this.handleClose}
                    aria-describedby="alert-dialog-slide-description"
                >
                    <DialogTitle>{"Confirm?"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-slide-description">
                            Well, even after the confirmation
                            nothing will happen, since this is an experimental feature
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose}>Disagree</Button>
                        <Button onClick={this.handleClose}>Agree</Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}
export default NewUserComponent
