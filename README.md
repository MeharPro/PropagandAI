# Backend
The main backend service. This is sort of the most monolithic portion of my conceptual architecture. It will handle **everything**. The client will direct all requests to endpoints on this service to the correct endpoints on the other services. Scalability and robustness are goals, so it may eventually need to integrate with a microservice manager.

# First commit mini notes for the bloggers

This initial version of the application lacks any direct way to train the models. This will be added as the next major feature. What this version does do is act as a scaffold for future versions.

### Thing I added RAG
I have implemented a rough RAG system to allow us to load information into the model's context on the spot from a JSON file. This will be helpful long-term because it means we can dynamically reinforce the model's learning without needing to fully retrain it. It's sort of like having a little guy on your shoulder that reads everything before you and hands you a list of things you should probably say before you're allowed to speak. I feel like this one should be longer after writing the other two, but the little shoulder guy is too funny to me. It is also ironic that as I wrote, my analogies got more technical instead of less :p.

### Thing I added Vector DB
In this version, I also have a basic DB implementation. It really isn’t fancy and will need to be changed later, but it is a Postgres DB with the PGVector plugin, so it can act as a vector database for the RAG system. Vector databases are basically databases of documents but in the raw mathematical forms that transformer models like ChatGPT actually read.

Postgres is also a form of relational database. A relational database is like a highly organized digital filing cabinet for storing information. Imagine that you were running a library where you need to keep track of books, authors, and borrowers. Instead of keeping everything in one giant list, you organize all the data into tables, which are kind of like spreadsheets. Each table stores a specific type of information. For example, a Books table might have columns for Title, Author, and Genre, while an Authors table could include Name, Birth Year, and Nationality. A Borrowers table would track Name, Contact Info, and Borrowed Books. These tables are related to each other using a key, which is a unique piece of information like an ID number. This might mean that each book has an Author ID that links it to the correct author in the Authors table, and each borrowed book has a Borrower ID connecting it to the person who checked it out. Because of these relationships, the database avoids storing the same information in multiple places. Instead of repeating an author’s name for every book they wrote, the system simply links the related data, making everything super efficient, organized, and easy to update later.

### Thing I added Docker
The final big tech thing I did was configure almost everything to run in Docker containers. This one is probably going to stay as a design requirement because of the massive amount of flexibility it adds. It means we can separate all the parts and move them to separate computers if we need to for speed in production. It also means that if one system fails, it can be restarted without bringing the others down with it.

My layman's explanation of Docker containers is lame, but here I go anyway... Imagine you and your friends have lunch at a park, and everyone brings their own lunchbox with everything they need. If someone drops theirs, they can grab an identical one from a cooler and keep eating. This makes our application way more reliable, where a failed container can be instantly replaced. If more friends arrive, you add more tables (as long as you have more compute), and everyone gets whatever meal they need to contribute to the conversation. This makes our application way more scalable, where more containers can handle increased demand.

Now, imagine some friends have things that they really like to talk about that no one else can. Maybe one really likes trains, another loves planes, and the last one loves things that rhyme with the last two. Even though each lunchbox is self-contained, these friends can still talk to each other through a shared picnic blanket, which is similar to how Docker containers communicate over an app network to share data and services while staying independent. Since every type of lunchbox has the same items, the meal is always consistent no matter where you put it down, just like how Docker ensures software runs the same anywhere.

Okay, I lied—that was actually a really sick analogy (YIPPEEE).

### General dev tools
No layman’s explanation here, just a list for anyone who cares. I set up PGAdmin to monitor calls to the database. Pretty helpful to make sure the files aren’t just building up constantly (they will do this if you let them :)). I set up Prometheus and Grafana so we have a dashboard to monitor the application's health and see if any errors are getting thrown. This will be way more important later when we have error handling that will hopefully try to prevent crashes but might obfuscate misbehavior without tracking.

And finally, the most classic part of any tech stack—unit tests! I installed JUnit, PIT, and Mockito, which I think might actually be default. This way, we can eventually build up a full test suite and actually measure coverage via mutation tests.

## Next Steps
The next big step, of course, is to add ***TRUE*** fine-tuning through LLAMA Factory. I want it to be integrated with a new DB that will track the model itself, the dataset it was trained on, and its name so that they can be loaded into the main application on the spot. Meaning the training data gets parsed into the vector DB, and we add that to the context.

The big limiting factors here are:

A) I have not had a real GPU for a week.

B) I do not know how I would dynamically load things into Ollama.

I did some two-model tests today, and it just KILLED my GPU, and I’m rocking a 4090. To be fair, it was DeepSeek-32B, but still.

Okay Bye now :D this rant was formated by chat gpt so hopefully it ends up being understandable to not 4:25 AM me. 

NEVERMIND I LIED

# How to run
To run you will need the Docker engine CLI can work but I recommend you install Docker desktop.
https://www.docker.com/products/docker-desktop/

You will also need Java 23 installed alongside maven for Java I added a sdkrc for any other sdkman users out there. Otherwise just install whatever from your package manager. If you are on windows and don't have one here is a link to SDKMAN for you to install on Windows subsystem linux and just use your IDE to install maven it is not worth fighting to do it manually ask me how I know.
https://sdkman.io/install

That is pretty much it I think. It helps often to run Docker compose up -d in the root of the project to download the containers before starting. It also is occasionally needed to manually pull models into ollama by running ollama pull modelName in the container before starting spring to do this you can run 

docker exec -it backend-ollama-llm ollama pull modelName


README - Credits: Bryan Peart

