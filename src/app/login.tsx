export default function login(){
    return(
        <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <div className="flex flex-col">
        <p className="text-red-800 flex justify-center">
          Login  to your account
        </p>
          <label>Username :</label>
          <input className="text-black border border-gray-300 px-4 py-2 rounded-md m-5" type="text"  id="username" name="username" />
              
          <label>Password: </label>
          <input className="text-black border border-gray-300 px-4 py-2 rounded-md m-5" type="password"  id="password" name="password"/>
        
          <button className=" bg-green-600 border m-4 w-12 h-13 rounded-md" name="login" id="login">Login</button>
      </div>
    </main>
    );
}