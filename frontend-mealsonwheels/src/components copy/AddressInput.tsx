import { useState, useRef, useCallback } from 'react';
import axios from 'axios';
import debounce from 'lodash.debounce'; // Install: npm install lodash.debounce

const LOCATIONIQ_API_KEY = "pk.80cf09e03cb70cea56965ea56862b570";

type Suggestion = {
  place_id: string | number;
  display_name: string;
  [key: string]: any;
};

interface AddressInputProps {
  onSelectAddress: (address: string) => void;
}

export default function AddressInput({ onSelectAddress }: AddressInputProps) {
  const [query, setQuery] = useState<string>("");
  const [suggestions, setSuggestions] = useState<Suggestion[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [activeIndex, setActiveIndex] = useState<number>(-1);
  const suggestionsRef = useRef<HTMLUListElement>(null);

  const fetchSuggestions = async (input: string) => {
    if (!input) return;
    try {
      setLoading(true);
      const res = await axios.get(`https://us1.locationiq.com/v1/autocomplete.php`, {
        params: {
          key: LOCATIONIQ_API_KEY,
          q: input,
          format: "json",
        },
      });
      setSuggestions(res.data);
    } catch (error) {
      console.error("Autocomplete error", error);
    } finally {
      setLoading(false);
    }
  };

  const debouncedFetch = useCallback(debounce(fetchSuggestions, 300), []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target.value;
    setQuery(input);
    setActiveIndex(-1);
    debouncedFetch(input);
  };

  const handleSelect = (suggestion: Suggestion) => {
    setQuery(suggestion.display_name);
    setSuggestions([]);
    onSelectAddress(suggestion.display_name);
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'ArrowDown') {
      e.preventDefault();
      setActiveIndex((prev) => Math.min(prev + 1, suggestions.length - 1));
    } else if (e.key === 'ArrowUp') {
      e.preventDefault();
      setActiveIndex((prev) => Math.max(prev - 1, 0));
    } else if (e.key === 'Enter') {
      e.preventDefault();
      if (activeIndex >= 0 && suggestions[activeIndex]) {
        handleSelect(suggestions[activeIndex]);
      }
    } else if (e.key === 'Escape') {
      setSuggestions([]);
    }
  };

  const handleUseCurrentLocation = () => {
    if (!navigator.geolocation) {
      alert("Geolocation is not supported by your browser");
      return;
    }

    navigator.geolocation.getCurrentPosition(async (position) => {
      const { latitude, longitude } = position.coords;
      try {
        const res = await axios.get(`https://us1.locationiq.com/v1/reverse.php`, {
          params: {
            key: LOCATIONIQ_API_KEY,
            lat: latitude,
            lon: longitude,
            format: "json",
          },
        });
        const address = res.data.display_name;
        setQuery(address);
        onSelectAddress(address);
      } catch (error) {
        console.error("Reverse geocoding error", error);
        alert("Failed to get address from coordinates");
      }
    }, () => {
      alert("Unable to retrieve your location");
    });
  };

  return (
    <div style={{ position: "relative", width: "100%" }}>
      <input
        type="text"
        placeholder="Enter your address"
        value={query}
        onChange={handleChange}
        onKeyDown={handleKeyDown}
        style={{ width: "100%", padding: "8px", boxSizing: "border-box", color: "black" }}
      />

      {loading && (
        <div style={{ padding: "8px", backgroundColor: "#fff", border: "1px solid #ccc" }}>
          Loading...
        </div>
      )}

      {suggestions.length > 0 && (
        <ul
          ref={suggestionsRef}
          style={{
            listStyle: "none",
            margin: 0,
            padding: 0,
            position: "absolute",
            top: "100%",
            left: 0,
            width: "100%",
            backgroundColor: "white",
            border: "1px solid #ccc",
            zIndex: 1000,
            maxHeight: "200px",
            overflowY: "auto",
            boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
            color: "black"
          }}
        >
          {suggestions.map((sugg, idx) => (
            <li
              key={sugg.place_id}
              onClick={() => handleSelect(sugg)}
              style={{
                padding: "8px",
                cursor: "pointer",
                borderBottom: "1px solid #eee",
                backgroundColor: idx === activeIndex ? "#f0f0f0" : "white"
              }}
              onMouseDown={(e) => e.preventDefault()}
            >
              {sugg.display_name}
            </li>
          ))}
        </ul>
      )}

      <button type="button" onClick={handleUseCurrentLocation} style={{ marginTop: "5px" }}>
        Use Current Location
      </button>
    </div>
  );
}
