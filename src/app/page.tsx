// import { useNavigation } from 'next/navigation';
import { useRouter } from 'next/router';

export default function Home() {
  const router = useRouter(); // Access the router instance

  const handleClick = () => {
    router.push('/login'); // Use router.push to navigate to the login page
  };

  return (
    <main>
      <div>
        <h1>Task Management System</h1>
        <button onClick={handleClick}>Go to Login</button>
      </div>
    </main>
  );
}
