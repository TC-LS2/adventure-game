import { routerMiddleware, connectRouter } from "connected-react-router"
import ducksReducer from "ducks-reducer"
import ducksMiddleware from "ducks-middleware"
import { createBrowserHistory } from "history"
import { createStore, applyMiddleware, compose } from "redux"

import freezeReducer from "./freezeReducer"

import * as alert from "./alert"
import * as game from "./game"
import * as login from "./login"
import * as worldBuilder from "./world-builder"

const ducks = { alert, game, login, worldBuilder }
const appReducer = ducksReducer(ducks)
const appMiddleware = ducksMiddleware(ducks)

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose
const createAdventureStore = (initialState, ...middleware) => {
  const history = createBrowserHistory()

  return {
    ...createStore(
      freezeReducer(connectRouter(history)(appReducer)),
      initialState,
      composeEnhancers(
        applyMiddleware(
          appMiddleware,
          routerMiddleware(history),
          ...middleware,
        ),
      ),
    ),
    history,
  }
}

export default createAdventureStore
