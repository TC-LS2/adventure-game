import React from "react"
import { Provider } from "react-redux"
import { ConnectedRouter } from "connected-react-router"

import createAdventureStore from "."

const makeDucksRoot = (initialState, ...middleware) => {
  const store = createAdventureStore(initialState, ...middleware)

  const DucksRoot = ({ children }) => (
    <div>
      <Provider store={store}>
        <ConnectedRouter history={store.history}>{children}</ConnectedRouter>
      </Provider>
    </div>
  )
  DucksRoot.store = store
  DucksRoot.history = store.history
  DucksRoot.DucksRoot = DucksRoot

  return DucksRoot
}

export default makeDucksRoot
