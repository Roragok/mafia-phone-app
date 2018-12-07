export const GET_GAMES = 'mafia-phone-app/games/LOAD';
export const GET_GAMES_SUCCESS = 'mafia-phone-app/games/LOAD_SUCCESS';
export const GET_GAMES_FAIL = 'mafia-phone-app/games/LOAD_FAIL';

export default function reducer(state = { games: [] }, action) {
  switch (action.type) {
    case GET_GAMES:
      return { ...state, loading: true };
    case GET_GAMES_SUCCESS:
      return { ...state, loading: false, games: action.payload.data };
    case GET_GAMES_FAIL:
      return {
        ...state,
        loading: false,
        error: 'Error while fetching games'
      };

    default:
      return state;
  }
}

export function getGames() {
  return {
    type: GET_GAMES,
    payload: {
      request: {
        url: `/getGames`
      }
    }
  };
}
