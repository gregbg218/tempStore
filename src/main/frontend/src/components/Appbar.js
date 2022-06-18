import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Link } from "react-router-dom";



export default function Appbar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed">
        <Toolbar>
          
            <Typography align="center" variant="h6" component="div" sx={{ flexGrow: 1 }}>
            <Link to="/" style={{ textDecoration: 'none' , color: '#FFF'}} >
              {/* <a> */}
              Temp Store
              {/* </a> */}
              </Link>
            </Typography>
          
        </Toolbar>
      </AppBar>
    </Box>
  );
}
