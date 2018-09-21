import getGame from "./getGame"

const hasGame = state => {
  const game = getGame(state)
  return !!(game && game.player && game.player.username)
}

export default hasGame
