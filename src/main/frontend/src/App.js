
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import User from './components/User';
import Home from './components/Home';

function App() {
  return (
    <Router>
      <div className="App">
        <div>
          Hey
        </div>
        <Routes>
          <Route exact path="/" element={<Home />} />

          <Route path="/user/:id" element={<User />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
