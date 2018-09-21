import React from "react"
import PostCommandButton from "./PostCommandButton"

const GameView = ({
  game: {
    player: { username, lifePoints, key, shield, weapon },
    room: { name, description, exits, item, monster },
  },
}) => (
  <div>
    <div>
      <PostCommandButton username={username} command="look">
        [ ↻ ]
      </PostCommandButton>
      {` ${username}`} ({lifePoints} pts)
      {key && ` Key: ${key.name}`}
      {shield && ` Shield: ${shield.name}`}
      {weapon && ` Weapon: ${weapon.name}`}
    </div>
    <h3>{name}</h3>
    <p>{description}</p>
    {exits &&
      exits.length > 0 && (
        <div>
          Exits:{" "}
          {exits
            .map(exit => `${exit.name}${exit.open ? "" : " (closed)"}`)
            .join(", ")}
          .
        </div>
      )}
    <div>
      {item && `There is a ${item.name}.`}
      {monster && `There is ${monster.name}.`}
    </div>
    <br />
    <div>
      {exits &&
        exits.map(exit => (
          <span key={exit.name}>
            <PostCommandButton
              username={username}
              command="move"
              arguments={[exit.name]}
            >
              [ {exit.name} ]
            </PostCommandButton>{" "}
          </span>
        ))}
      {item && (
        <span>
          <PostCommandButton username={username} command="get">
            [ get ]
          </PostCommandButton>{" "}
        </span>
      )}
      {monster && (
        <PostCommandButton username={username} command="attack">
          [ attack ]
        </PostCommandButton>
      )}
    </div>
  </div>
)
export default GameView
