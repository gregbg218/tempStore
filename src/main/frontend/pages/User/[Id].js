import styles from '../../styles/Home.module.css'
import Appbar from '../components/Appbar'
import FileTable from '../components/FileTable'
import Upload from '../components/Upload'



export default function User() {
  return (
    <div className={styles.container}>
      <Appbar />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <FileTable />
      <Upload buttonFlag={true}/>
    </div>
  )
}
