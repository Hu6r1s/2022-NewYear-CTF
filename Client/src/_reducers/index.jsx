import { combineReducers } from "redux";
import user from "./UserReducer";
import challenge from "./ChallengeReducer";
import promiseMiddlerware from "redux-promise";
import { createStore, applyMiddleware, compose } from "redux";
import { persistStore, persistReducer } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";
import thunk from "redux-thunk";
const persistConfig = {
    key: "root",
    storage: storageSession,
    blacklist: ["challenge"]
};
const rootReducer = combineReducers({
    user,
    challenge
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

const devToolsExtension = window.__REDUX_DEVTOOLS_EXTENSION__;
const composedEnhancers = compose(applyMiddleware(thunk), applyMiddleware(promiseMiddlerware));

export default () => {
    const store = createStore(persistedReducer, composedEnhancers);
    return { store, persistor: persistStore(store) };
};
