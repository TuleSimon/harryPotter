
import com.simon.data.data.network.repositories.NetworkRepositoryImpl
import com.simon.data.data.network.repositories.NetworkResult
import com.simon.data.models.characters.CharactersResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class NetworkRepositoryImplTest {

    private lateinit var client: HttpClient
    private lateinit var repository: NetworkRepositoryImpl

    @Before
    fun setup() {

        client =   HttpClient(MockEngine) {
            engine {
               addHandler {
                   respond(
                       dummyData,
                       headers = headersOf(
                           HttpHeaders.ContentType,
                           ContentType.Application.Json.toString()
                       )
                   )
               }
                }
            install(ContentNegotiation) {

                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                    contentType = ContentType.Application.Json
                )
            }

        }

        repository = NetworkRepositoryImpl(client)
    }

    @After
    fun tearDown() {
        client.close()
    }

    @Test
    fun `getCharacters should return Success with list of characters`() = runTest {
        // When
        (client.engine as MockEngine).config.addHandler {
            respond(
                dummyData,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            ) }
        val mainList= Json.decodeFromString<List<CharactersResponseItem>>(dummyData)
      
        val result2 = repository.getCharacters().last()

        // Then
        assertEquals(
            NetworkResult.Success(mainList),
            result2)
    }

    @Test
    fun `getCharacters should return Loading for first emittion`() = runTest {
        // When
        val result = repository.getCharacters().first()

        // Then
        assertEquals(
            NetworkResult.Loading,
            result)
        
    }

    @Test
    fun `getCharacters should return Failure if API returns 404 error`() = runTest {
        // Given
        (client.engine as MockEngine).config.apply {
            requestHandlers.clear()
            addHandler { respondError(HttpStatusCode.NotFound) }
        }

        // When
        val result = repository.getCharacters().last()

        // Then
        assert(result is NetworkResult.Failure)
    }

    @Test
    fun `getCharacters should return Failure if API returns 500 error`() = runTest {
        // Given
        (client.engine as MockEngine).config.apply {
            requestHandlers.clear()
            addHandler { respondError(HttpStatusCode.InternalServerError) }
        }
        // When
        val result = repository.getCharacters().last()

        // Then
        assert(result is NetworkResult.Failure)
    }



}




const val dummyData = """[
    {
        "id": "9e3f7ce4-b9a7-4244-b709-dae5c1f1d4a8",
        "name": "Harry Potter",
        "alternate_names": [
        "The Boy Who Lived",
        "The Chosen One"
        ],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "31-07-1980",
        "yearOfBirth": 1980,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "green",
        "hairColour": "black",
        "wand": {
        "wood": "holly",
        "core": "phoenix feather",
        "length": 11
    },
        "patronus": "stag",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Daniel Radcliffe",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/harry.jpg"
    },
    {
        "id": "4c7e6819-a91a-45b2-a454-f931e4a7cce3",
        "name": "Hermione Granger",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Gryffindor",
        "dateOfBirth": "19-09-1979",
        "yearOfBirth": 1979,
        "wizard": true,
        "ancestry": "muggleborn",
        "eyeColour": "brown",
        "hairColour": "brown",
        "wand": {
        "wood": "vine",
        "core": "dragon heartstring",
        "length": 10.75
    },
        "patronus": "otter",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Emma Watson",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/hermione.jpeg"
    },
    {
        "id": "c3b1f9a5-b87b-48bf-b00d-95b093ea6390",
        "name": "Ron Weasley",
        "alternate_names": [
        "Dragomir Despard"
        ],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "01-03-1980",
        "yearOfBirth": 1980,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "blue",
        "hairColour": "red",
        "wand": {
        "wood": "willow",
        "core": "unicorn tail-hair",
        "length": 14
    },
        "patronus": "Jack Russell terrier",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Rupert Grint",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/ron.jpg"
    },
    {
        "id": "af95bd8a-dfae-45bb-bc69-533860d34129",
        "name": "Draco Malfoy",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": "05-06-1980",
        "yearOfBirth": 1980,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "grey",
        "hairColour": "blonde",
        "wand": {
        "wood": "hawthorn",
        "core": "unicorn tail-hair",
        "length": 10
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Tom Felton",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/draco.jpg"
    },
    {
        "id": "ca3827f0-375a-4891-aaa5-f5e8a5bad225",
        "name": "Minerva McGonagall",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Gryffindor",
        "dateOfBirth": "04-10-1925",
        "yearOfBirth": 1925,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "",
        "hairColour": "black",
        "wand": {
        "wood": "fir",
        "core": "dragon heartstring",
        "length": 9.5
    },
        "patronus": "tabby cat",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Dame Maggie Smith",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/mcgonagall.jpg"
    },
    {
        "id": "d5c4daa3-c726-426a-aa98-fb40f3fba816",
        "name": "Cedric Diggory",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Hufflepuff",
        "dateOfBirth": null,
        "yearOfBirth": 1977,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "grey",
        "hairColour": "brown",
        "wand": {
        "wood": "ash",
        "core": "unicorn hair",
        "length": 12.25
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Robert Pattinson",
        "alternate_actors": [],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/cedric.png"
    },
    {
        "id": "8f9aa40b-5d7c-441e-ad32-4564ecda3b70",
        "name": "Cho Chang",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Ravenclaw",
        "dateOfBirth": "07-04-1979",
        "yearOfBirth": 1979,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "brown",
        "hairColour": "black",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "swan",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Katie Leung",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/cho.jpg"
    },
    {
        "id": "3569d265-bd27-44d8-88e8-82fb0a848374",
        "name": "Severus Snape",
        "alternate_names": [
        "Half-Blood Prince"
        ],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": "09-01-1960",
        "yearOfBirth": 1960,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "black",
        "hairColour": "black",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "doe",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Alan Rickman",
        "alternate_actors": [],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/snape.jpg"
    },
    {
        "id": "36bfefd0-e0bb-4d11-be98-d1ef6117a77a",
        "name": "Rubeus Hagrid",
        "alternate_names": [],
        "species": "half-giant",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "06-12-1928",
        "yearOfBirth": 1928,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "black",
        "hairColour": "black",
        "wand": {
        "wood": "oak",
        "core": "",
        "length": 16
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Robbie Coltrane",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/hagrid.png"
    },
    {
        "id": "3db6dc51-b461-4fa4-a6e4-b1ff352221c5",
        "name": "Neville Longbottom",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "30-07-1980",
        "yearOfBirth": 1980,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "blonde",
        "wand": {
        "wood": "cherry",
        "core": "unicorn tail-hair",
        "length": 13
    },
        "patronus": "Non-Corporeal",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Matthew Lewis",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/neville.jpg"
    },
    {
        "id": "861c4cde-2f0f-4796-8d8f-9492e74b2573",
        "name": "Luna Lovegood",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Ravenclaw",
        "dateOfBirth": "13-02-1981",
        "yearOfBirth": 1981,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "grey",
        "hairColour": "blonde",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "hare",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Evanna Lynch",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/luna.jpg"
    },
    {
        "id": "1cd6dc64-01a9-4379-9cfd-1a7167ba1bb1",
        "name": "Ginny Weasley",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Gryffindor",
        "dateOfBirth": "11-08-1981",
        "yearOfBirth": 1981,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "brown",
        "hairColour": "red",
        "wand": {
        "wood": "yew",
        "core": "",
        "length": null
    },
        "patronus": "horse",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Bonnie Wright",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/ginny.jpg"
    },
    {
        "id": "2cfd2d4b-5d1e-4dc5-8837-37a97c7e2f2f",
        "name": "Sirius Black",
        "alternate_names": [
        "Padfoot",
        "Snuffles"
        ],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "03-11-1959",
        "yearOfBirth": 1959,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "grey",
        "hairColour": "black",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "hare",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Gary Oldman",
        "alternate_actors": [
        "James Walters",
        "Rohan Gotobed"
        ],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/sirius.JPG"
    },
    {
        "id": "b8f9095b-9de6-4d7d-83e0-4391af8f22e4",
        "name": "Remus Lupin",
        "alternate_names": [
        "Professor Lupin",
        "Moony",
        "Remus John Lupin"
        ],
        "species": "werewolf",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "10-03-1960",
        "yearOfBirth": 1960,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "green",
        "hairColour": "brown",
        "wand": {
        "wood": "cypress",
        "core": "unicorn tail-hair",
        "length": 10.25
    },
        "patronus": "wolf",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "David Thewlis",
        "alternate_actors": [],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/lupin.jpg"
    },
    {
        "id": "dd925874-e800-4eb4-9f0d-4d0fed15634b",
        "name": "Arthur Weasley",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "06-02-1950",
        "yearOfBirth": 1950,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "blue",
        "hairColour": "red",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "weasel",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Mark Williams",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/arthur.jpg"
    },
    {
        "id": "6afb1960-febd-418d-b604-e50c1b59459b",
        "name": "Bellatrix Lestrange",
        "alternate_names": [
        "Bella"
        ],
        "species": "human",
        "gender": "female",
        "house": "Slytherin",
        "dateOfBirth": null,
        "yearOfBirth": 1951,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "brown",
        "hairColour": "black",
        "wand": {
        "wood": "walnut",
        "core": "dragon heartstring",
        "length": 12.75
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Helena Bonham Carter",
        "alternate_actors": [],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/bellatrix.jpg"
    },
    {
        "id": "efa802c8-ae18-4ae1-a524-49df21d05939",
        "name": "Lord Voldemort",
        "alternate_names": [
        "Tom Marvolo Riddle"
        ],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": "31-12-1926",
        "yearOfBirth": 1926,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "red",
        "hairColour": "bald",
        "wand": {
        "wood": "yew",
        "core": "phoenix feather",
        "length": 13.5
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Ralph Fiennes",
        "alternate_actors": [],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/voldemort.jpg"
    },
    {
        "id": "2fb675cd-5505-4c8e-a54e-579e73bf4174",
        "name": "Horace Slughorn",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "green",
        "hairColour": "blonde",
        "wand": {
        "wood": "cedar",
        "core": "dragon heartstring",
        "length": 10.25
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Jim Broadbent",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/slughorn.JPG"
    },
    {
        "id": "106cf68d-a86a-415e-ad75-6ad9a4ae24e4",
        "name": "Kingsley Shacklebolt",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "brown",
        "hairColour": "brown",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "lynx",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "George Harris",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/kingsley.jpg"
    },
    {
        "id": "d58e7249-19d1-40bd-a43f-1da0497fe8aa",
        "name": "Dolores Umbridge",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Slytherin",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "brown",
        "hairColour": "grey",
        "wand": {
        "wood": "birch",
        "core": "dragon heartstring",
        "length": 8
    },
        "patronus": "persian cat",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Imelda Staunton",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/umbridge.jpg"
    },
    {
        "id": "43403619-70cb-4a0c-b70a-6d5cae20e602",
        "name": "Lucius Malfoy",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": null,
        "yearOfBirth": 1954,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "grey",
        "hairColour": "blonde",
        "wand": {
        "wood": "elm",
        "core": "dragon heartstring",
        "length": 18
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Jason Isaacs",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/lucius.jpg"
    },
    {
        "id": "04f9eb45-d843-4e29-a7d3-0bd49ed87f85",
        "name": "Vincent Crabbe",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "black",
        "hairColour": "black",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Jamie Waylett",
        "alternate_actors": [],
        "alive": false,
        "image": "https://ik.imagekit.io/hpapi/crabbe.jpg"
    },
    {
        "id": "a31ddc78-af12-4978-929c-3cc8a00a833e",
        "name": "Gregory Goyle",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Slytherin",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "brown",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Josh Herdman",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/goyle.jpg"
    },
    {
        "id": "b0620914-858d-46fc-8e6d-033c565e138b",
        "name": "Mrs Norris",
        "alternate_names": [],
        "species": "cat",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "",
        "eyeColour": "yellow",
        "hairColour": "brown",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Maxime, Alanis and Tommy the cats",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/norris.JPG"
    },
    {
        "id": "2b82cfb8-0440-4a57-a030-6d75a40c0d98",
        "name": "Argus Filch",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "squib",
        "eyeColour": "",
        "hairColour": "grey",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "David Bradley",
        "alternate_actors": [],
        "alive": true,
        "image": "https://ik.imagekit.io/hpapi/filch.jpg"
    },
    {
        "id": "b4ffe89b-8796-42ad-a6d9-5af1a369af7e",
        "name": "Vernon Dursley",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Richard Griffiths",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "fed624df-56d9-495e-9ad4-ea77000957e8",
        "name": "Petunia Dursley",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "blonde",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Fiona Shaw",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "7614cf6e-689e-47ac-a976-b1e9997637e9",
        "name": "Dudley Dursley",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": "23-6-1980",
        "yearOfBirth": 1980,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "blue",
        "hairColour": "blond",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Harry Melling",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "41cd0bbe-a943-431b-9bde-bb2cad3491a1",
        "name": "Lily Potter",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "Gryffindor",
        "dateOfBirth": "30-01-1960",
        "yearOfBirth": 1960,
        "wizard": true,
        "ancestry": "muggleborn",
        "eyeColour": "green",
        "hairColour": "",
        "wand": {
        "wood": "willow",
        "core": "",
        "length": 10.25
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Geraldine Somerville",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "34eb6182-00cf-4c95-ae73-7d2f34066d18",
        "name": "James Potter",
        "alternate_names": [
        "Prongs"
        ],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": "27-03-1960",
        "yearOfBirth": 1960,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "black",
        "wand": {
        "wood": "mahogany",
        "core": "",
        "length": 11
    },
        "patronus": "stag",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Adrian Rawlins",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "b415c867-1cff-455e-b194-748662ac2cca",
        "name": "Albus Dumbledore",
        "alternate_names": [
        "Professor Dumbledore"
        ],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": null,
        "yearOfBirth": 1881,
        "wizard": true,
        "ancestry": "half-blood",
        "eyeColour": "blue",
        "hairColour": "silver",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Richard Harris",
        "alternate_actors": [
        "Michael Gambon"
        ],
        "alive": false,
        "image": ""
    },
    {
        "id": "e9457467-d10a-4893-afa9-19f9602b218a",
        "name": "Madam Pomfrey",
        "alternate_names": [
        "Poppy Pomfrey"
        ],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Gemma Jones",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "63c6fcff-a1a4-4caf-a5ac-e2197c12cb1e",
        "name": "Mrs Figg",
        "alternate_names": [
        "Arabella Doreen Figg"
        ],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "squib",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Kathryn Hunter",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "162b34f3-aaab-46ae-980b-f2a5ebceb684",
        "name": "Marge Dursley",
        "alternate_names": [
        "Marjorie Eileen Dursley",
        "Aunt Marge",
        "Auntie Marge",
        "Margie"
        ],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Pam Ferris",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "d70f60ae-81ea-4230-905d-cb52c801c1fb",
        "name": "Yvonne",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "57fa4f38-a1ff-4671-890d-49af38b2a619",
        "name": "Piers Polkiss",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Jason Boyd",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "a18ef59a-89cf-4afd-bf87-83273990d911",
        "name": "Dennis",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Christopher Rithin",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "ef33d1e6-427c-465e-9a9f-037442b376d3",
        "name": "Malcolm",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Richard Macklin",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "71a95495-134d-4af7-8473-5f3495979845",
        "name": "Gordon",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "muggle",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "933787c2-51e3-4eac-8a85-ab332cac0456",
        "name": "Miranda Gaushawk",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "8b5bbdda-6a45-409c-99b7-0c64e69ed611",
        "name": "Bathilda Bagshot",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "8674a5d1-b2ba-4025-ac6d-4d1aa3f49ad9",
        "name": "Adalbert Waffling",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "cd98821f-04a8-4df7-9098-64ab117ede00",
        "name": "Emeric Switch",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "19fa7e4e-dadd-46a8-912e-51a1f622ce0e",
        "name": "Phyllida Spore",
        "alternate_names": [
        "Dame Phyllida Spore"
        ],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "f859a0ab-0401-4791-b8f5-d3b2bc9668ca",
        "name": "Arsenius Jigger",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "2a376401-4821-4aec-94d7-9b3da2c77d85",
        "name": "Newt Scamander",
        "alternate_names": [
        "Newton Artemis Fido Scamander"
        ],
        "species": "human",
        "gender": "male",
        "house": "Hufflepuff",
        "dateOfBirth": "24-02-1897",
        "yearOfBirth": 1897,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "hazel",
        "hairColour": "brown",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Eddie Redmayne",
        "alternate_actors": [
        "Joshua Shea",
        "Callum Turner"
        ],
        "alive": true,
        "image": ""
    },
    {
        "id": "aa83dd83-beb5-43ef-b4a1-9bb2389edc44",
        "name": "Quentin Trimble",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "1e705d10-aa89-4e9c-94be-478304d55dbb",
        "name": "Tom",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Derek Deadman",
        "alternate_actors": [
        "Jim Tavare"
        ],
        "alive": true,
        "image": ""
    },
    {
        "id": "43b2880a-df86-4f55-a599-9cd0548833f4",
        "name": "Doris Crockford",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Nina Young",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "ba19be27-178b-4594-95b7-51ba0e3ba1dd",
        "name": "Quirinus Quirrel",
        "alternate_names": [
        "Professor Quirrel"
        ],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": true,
        "actor": "Ian Hart",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "4bd76a78-86d8-40f6-aed4-74fe51783594",
        "name": "Griphook",
        "alternate_names": [],
        "species": "goblin",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Verne Troyer",
        "alternate_actors": [
        "Warwick Davis"
        ],
        "alive": false,
        "image": ""
    },
    {
        "id": "7b26f1db-7cc5-4b66-9f2f-6bbb04d628b2",
        "name": "Madam Malkin",
        "alternate_names": [],
        "species": "human",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "ba05fee0-483c-42ed-a8b6-b7a82b6e800a",
        "name": "Vindictus Viridian",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "d59691a4-f830-4eb0-a819-a0fb00b7e80f",
        "name": "Garrick Ollivander",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "pale, silvery",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "John Hurt",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "6b59be3f-e527-422d-959d-79fcdb3b24eb",
        "name": "Hedwig",
        "alternate_names": [],
        "species": "owl",
        "gender": "female",
        "house": "",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": false,
        "ancestry": "",
        "eyeColour": "amber",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Gizmo",
        "alternate_actors": [
        "Ook",
        "Sprout",
        "Kasper",
        "Swoops",
        "Oh Oh",
        "Elmo",
        "Bandit"
        ],
        "alive": false,
        "image": ""
    },
    {
        "id": "79e02eb5-17c0-4dd9-bf55-aec03434812c",
        "name": "Molly Weasley",
        "alternate_names": [
        "Molly Prewett",
        "Mollywabbles"
        ],
        "species": "human",
        "gender": "female",
        "house": "Gryffindor",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "red",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": false,
        "hogwartsStaff": false,
        "actor": "Julie Walters",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "8f3b8796-c7b9-442e-ac02-113d48306fc7",
        "name": "Percy Weasley",
        "alternate_names": [
        "Perce",
        "Percy Ignatius Weasley",
        "Weatherby"
        ],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "red",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Chris Rankin",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "28741184-263c-4000-b011-ca7c60466ef4",
        "name": "Fred Weasley",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "red",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "James Phelps",
        "alternate_actors": [],
        "alive": false,
        "image": ""
    },
    {
        "id": "2a0615de-8aa4-41e7-9504-dd875f5f3f01",
        "name": "George Weasley",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "pure-blood",
        "eyeColour": "",
        "hairColour": "red",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Oliver Phelps",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    },
    {
        "id": "979ab773-944f-4ff8-88be-943a4bc2c18a",
        "name": "Lee Jordan",
        "alternate_names": [],
        "species": "human",
        "gender": "male",
        "house": "Gryffindor",
        "dateOfBirth": null,
        "yearOfBirth": null,
        "wizard": true,
        "ancestry": "",
        "eyeColour": "",
        "hairColour": "",
        "wand": {
        "wood": "",
        "core": "",
        "length": null
    },
        "patronus": "",
        "hogwartsStudent": true,
        "hogwartsStaff": false,
        "actor": "Luke Youngblood",
        "alternate_actors": [],
        "alive": true,
        "image": ""
    } 
]"""
