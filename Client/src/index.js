import React from "react";
import ReactDOM from "react-dom";
import App from "./components/App";
import { Provider } from "react-redux";
import { applyMiddleware, createStore } from "redux";
import promiseMiddlerware from "redux-promise";
import reduxThunk from "redux-thunk";
import configureStore from "./_reducers";
import { PersistGate } from "redux-persist/integration/react";
const createStoreWidthMiddleware = applyMiddleware(promiseMiddlerware, reduxThunk)(createStore);
const { persistor, store } = configureStore();

ReactDOM.render(
  <React.StrictMode>
      <Provider store={store}>
          <PersistGate loading={null} persistor={persistor}>
              <App />
          </PersistGate>
      </Provider>
  </React.StrictMode>,
  document.getElementById("root")
);
  