import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {App} from './App';
import $ from 'jquery';
window.jQuery = window.$ = $;

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);
