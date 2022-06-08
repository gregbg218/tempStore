import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';


export default function Appbar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed">
        <Toolbar>
          <Typography to="/" align="center" variant="h6" component="div" sx={{ flexGrow: 1 }}>
          <Link href="http://localhost:3000/">
            Temp Store
            </Link>
          </Typography>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
