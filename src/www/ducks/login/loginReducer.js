import { SET_USERNAME } from "./actions/setUsername"

const initialState = {
  username: "",
}

const loginReducer = (state = initialState, action) => {
  switch (action.type) {
    case SET_USERNAME: {
      const { username } = action

      return { ...state, username }
    }
    default:
      return state
  }
}

export default loginReducer
