export const GET_DAYS = 'mafia-phone-app/days/LOAD';
export const GET_DAYS_SUCCESS = 'mafia-phone-app/days/LOAD_SUCCESS';
export const GET_DAYS_FAIL = 'mafia-phone-app/day/LOAD_FAIL';

export default function reducer(state = { days: [] }, action) {
  switch (action.type) {

      case GET_DAYS:
        return { ...state, loading: true };
      case GET_DAYS_SUCCESS:
        return { ...state, loading: false, days: action.payload.data };
      case GET_DAYS_FAIL:
        return {
          ...state,
          loading: false,
          error: 'Error while fetching days'
        };
    default:
      return state;
  }
}


export function getGameDays(game) {
  return {
    type: GET_DAYS,
    payload: {
      request: {
        url: `/getDays/${game}`
      }
    }
  };
}
